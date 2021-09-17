package framboos

import framboos.ApiClient.SolutionResponse
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class KnowledgeTest extends AnyFlatSpec {
  "merge" should "use the new round" in {
    val o = Knowledge(4, 5, Vector())

    val r = Knowledge.merge(o, o.copy(round = 7))

    r.round shouldBe 7
  }

//  "merge" should "use the new number of letters when roundnr is same" in {
//    val o = Knowledge(4, 5, Vector())
//
//    val r = Knowledge.merge(o, o.copy(numLetters = 7))
//
//    r.numLetters shouldBe 7
//  }

  "merge" should "use the new number of letters when new > old" in {
    val o = Knowledge(4, 5, Vector())
    val n = Knowledge(5, 7, Vector())

    val r = Knowledge.merge(o, n)

    r.numLetters shouldBe 7
  }

//  "merge" should "use the new number of letters when new < old" in {
//    val o = Knowledge(6, 5, Vector())
//    val n = Knowledge(5, 7, Vector())
//
//    val r = Knowledge.merge(o, n)
//
//    r.numLetters shouldBe 5
//  }

  "merge" should "discard knowledge of an old round" in {
    val o = Knowledge(4, 2, Vector(Some('a'), None))
    val n = Knowledge(5, 2, Vector(None, Some('b')))

    val r = Knowledge.merge(o, n)

    r shouldBe n
  }

  "merge" should "merge correct letters" in {
    val o = Knowledge(4, 2, Vector(Some('a'), None))
    val n = Knowledge(4, 2, Vector(None, Some('b')))

    val r = Knowledge.merge(o, n)

    r shouldBe Knowledge(4, 2, Vector(Some('a'), Some('b')))
  }

  "merge" should "merge correct with different lengths" in {
    val o = Knowledge(4, 5, Vector())
    val n = Knowledge(4, 2, Vector(None, Some('b')))

    val r = Knowledge.merge(o, n)

    r shouldBe Knowledge(4, 2, Vector(None, Some('b')))
  }

  "merge" should "discard old correct letters if the new length is smaller" in {
    val o = Knowledge(4, 5, Vector(None, None, None))
    val n = Knowledge(4, 2, Vector(None, Some('b')))

    val r = Knowledge.merge(o, n)

    r shouldBe n
  }

  "merge" should "discard old correct letters if the new length is larger" in {
    val o = Knowledge(4, 3, Vector(None, None, None))
    val n = Knowledge(4, 6, Vector(None, Some('b'), None, None, Some('r'), None))

    val r = Knowledge.merge(o, n)

    r shouldBe n
  }

  "fromResponse" should "work for only dots" in {
    val r = SolutionResponse("INCORRECT", 3, 2, "...")

    val k = Knowledge.fromResponse(r, "abc")

    k shouldBe Knowledge(2, 3, Vector(None, None, None))
  }

  "fromResponse" should "work for correct letters" in {
    val r = SolutionResponse("INCORRECT", 5, 2, "^t..^")

    val k = Knowledge.fromResponse(r, "abcde")

    k shouldBe Knowledge(2, 5, Vector(Some('a'), Some('t'), None, None, Some('e')))
  }

  "fromResponse" should "work if the new length is more" in {
    val r = SolutionResponse("INCORRECT", 3, 2, "...")

    val k = Knowledge.fromResponse(r, "ab")

    k shouldBe Knowledge(2, 3, Vector(None, None, None))
  }

  "fromResponse" should "work if the new length is less" in {
    val r = SolutionResponse("INCORRECT", 3, 2, "...")

    val k = Knowledge.fromResponse(r, "abcde")

    k shouldBe Knowledge(2, 3, Vector(None, None, None))
  }


}
