# Interview Service — Project Analysis

## Overview

A Java 17 library for **dynamic data-capture** driven by external YAML definitions. It builds an in-memory tree of questions and containers at runtime, then supports "splitting" — duplicating sections of that tree so the same set of questions can be answered multiple times (e.g., for each counterparty in a trade, or for each leg in a sequence).

- **Build**: Maven, Spring Boot 2.5.6 parent
- **Key dependency**: `jackson-dataformat-yaml` for YAML definition parsing
- **Entry point**: No runnable application — this is a library/service component

---

## Architecture

### Layer 1 — Definition (YAML → POJOs)

| Class | Role |
|---|---|
| `CaptureDefinition` | Root definition: id, name, list of questions |
| `QuestionDefinition` | A question with its container path, split type, and condition |
| `ContainerDefinition` | A node in the path hierarchy (id, name, optional split type) |

YAML files (e.g. `capture1.yml`) are deserialised into `CaptureDefinition` objects via Jackson.

### Layer 2 — Runtime Model (Interview Tree)

```
Interview
└── InterviewContainer  (e.g. "clause 1")
    └── InterviewContainer  (e.g. "group 1")
        └── InterviewQuestion  (e.g. "q1")
            └── Answer  (e.g. "ans1", "ans2")
```

All tree nodes extend the abstract `InterviewNode`, which carries an `id`, a reference to `parent`, and an optional `Split` descriptor.

| Class | Role |
|---|---|
| `InterviewNode` | Abstract base: id, parent, split descriptor, path-cache lookup |
| `InterviewContainer` | Branch node holding child `InterviewNode`s |
| `InterviewQuestion` | Leaf node with a question string and a set of `Answer`s |
| `Answer` | Terminal node (leaf of a leaf) |
| `InterviewNodePath` | XPath-like path reference, e.g. `["c1", "g1", "q1[SN=2]"]` |
| `Split` | Metadata: `SplitType` + sequence number + whether already split |
| `SplitType` | Enum: `SEQUENCE` or `COUNTERPARTY` |

Both `Interview` and `InterviewNode` maintain an internal `HashMap` path-cache for O(1) child lookup.

### Layer 3 — Services

| Class | Role |
|---|---|
| `InterviewDefinitionHandler` | Converts `CaptureDefinition` → `Interview` tree |
| `InterviewProcessor` | Applies split operations to an existing `Interview` |
| `InterviewUtility` | Tree traversal helpers (e.g. `getAllQuestions`) |
| `SplitNotValidException` | Custom exception (currently unused) |

### Split Mechanics

A **sequence split** duplicates a node (question or container) and appends the copy to the same parent, incrementing the sequence number. The original is marked as already-split (`isSplit=true`). The path cache key for sequence-split copies uses the format `id[SN=N]`.

A **counterparty split** is defined (`SplitType.COUNTERPARTY`, `SplitRequestCounterparty`) but **not yet implemented**.

---

## File Map

```
src/
  main/
    java/com/dm/interview_service/
      definition/
        CaptureDefinition.java
        ContainerDefinition.java
        QuestionDefinition.java
      exceptions/
        SplitNotValidException.java
      model/
        Answer.java
        Interview.java
        InterviewContainer.java
        InterviewNode.java
        InterviewNodePath.java
        InterviewQuestion.java
        Split.java
        SplitRequest.java
        SplitRequestCounterparty.java
        SplitRequestSequence.java
        SplitType.java
      service/
        InterviewDefinitionHandler.java
        InterviewProcessor.java
        InterviewUtility.java
    resources/
      definitions/
        capture1.yml          ← empty placeholder
  test/
    java/com/dm/interview_service/
      TestBasicStructure.java
      TestInterviewSplits.java
    resources/
      definitions/
        simpleDef1.yaml
```

---

## Recommendations

### High Priority

**1. `SplitRequestCounterparty` should extend `SplitRequest`**

`SplitRequestCounterparty` duplicates the exact fields (`idToSplit`, `nodePath`) already defined in the abstract `SplitRequest`, but doesn't extend it. `SplitRequestSequence` does extend `SplitRequest` correctly. Fix:

```java
public class SplitRequestCounterparty extends SplitRequest {
    public SplitRequestCounterparty(String idToSplit, InterviewNodePath nodePath) {
        super(idToSplit, nodePath);
    }
}
```

**2. Tests write files into `src/main/resources/` — production source from test code**

`TestBasicStructure.testWriteSimpleDefinition` and `testCreateSimpleDefinition` write output to `src/main/resources/definitions/`. Tests should write to `target/` or use a temp directory. The `TestInterviewSplits` suite also reads from `src/main/resources/`, creating an implicit build-time ordering dependency between test classes.

**3. `TestInterviewSplits` shares mutable state across tests**

The `interview` field is loaded once in `@BeforeAll` then mutated by each `@Test`. The outcome of `testSequenceSplitGroup` depends on what `testSequenceSplit` did. Either reset state in `@BeforeEach`, or make each test load its own interview.

**4. Unsafe cast in `InterviewUtility.getAllLeafNodes`**

Line 29 unconditionally casts child nodes to `InterviewContainer`. If the tree ever has a non-container non-question node in an intermediate position this will throw `ClassCastException`. Use `instanceof` before casting.

**5. `InterviewContainer.split()` calls `node.split(false)` twice**

```java
// current (line 62-63):
InterviewNode newNode = node.split(false);
if(newNode!=null) {
    newNodes.add(node.split(false));  // ← called again unnecessarily
}
// fix:
if(newNode != null) {
    newNodes.add(newNode);
}
```

### Medium Priority

**6. Spring Boot is unused — replace with plain Maven**

`spring-boot-starter-parent` brings hundreds of transitive dependencies. There is no `@SpringBootApplication`, no beans, no HTTP layer. Switch the parent to `maven-wrapper` or a plain Maven parent and keep only `jackson-dataformat-yaml` and JUnit 5 as dependencies. Also: Spring Boot 2.5.6 is end-of-life; if Spring Boot is retained, upgrade to 3.x.

**7. `SplitNotValidException` is never thrown**

It's defined but has zero usages. Either throw it from `InterviewProcessor.splitNode` on invalid input (replacing the silent `return false`), or delete it.

**8. `InterviewProcessor.splitNode(Interview, SplitRequestCounterparty)` is a stub**

The method body is empty and unconditionally returns `true`. Either implement it or throw `UnsupportedOperationException` so callers aren't misled.

**9. `InterviewQuestion.split()` has a logic bug — missing `else`**

The second condition (`} if(this.getSplit() == null && !splitRoot)`) runs whether or not the first condition matched, because it's `} if(` not `} else if(`. This means two branches can execute. Audit and add the missing `else`.

### Low Priority

**10. Remove commented-out code**

Two files contain an identical commented-out line that was clearly a debugging experiment:
- `Interview.java:50`
- `InterviewNode.java:45`

`TestBasicStructure.java:120` also has a commented-out `System.out.println`.

**11. Hardcoded path separators**

`"src//main//resources//definitions//simpleDef1.yaml"` uses double-slash separators throughout the tests. Prefer `Path.of("src", "main", ...)` or load as classpath resources.

**12. `capture1.yml` is empty**

The main-resources definition file has no content. Either populate it or delete it to avoid confusion.

**13. Duplicated path-cache logic between `Interview` and `InterviewNode`**

Both classes implement near-identical `refreshNodePathCache` and `generateNodePathString` methods. Since `Interview` is not an `InterviewNode`, extraction into a shared utility or interface would reduce the duplication.
