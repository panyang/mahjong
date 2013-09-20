package websiteschema.mpsegment.performance

import junit.framework.Assert
import org.junit.{Ignore, Test}
import websiteschema.mpsegment.core.SegmentEngine
import websiteschema.mpsegment.tools.accurary.ErrorAnalyzer
import websiteschema.mpsegment.tools.accurary.SegmentAccuracy
import websiteschema.mpsegment.tools.accurary.SegmentErrorType._

class AccuracyTest {

  @Test
  def should_be_higher_than_93_percent_with_segment_minimum() {
    val segmentWorker =
      SegmentEngine().getSegmentWorker(
        "separate.xingming -> true",
        "minimize.word -> true"
      )
    val segmentAccuracy = new SegmentAccuracy("PFR-199801-utf-8.txt", segmentWorker)
//    val segmentAccuracy = new SegmentAccuracy("test-pfr-corpus.txt", segmentWorker)
    segmentAccuracy.checkSegmentAccuracy()
    println("Accuracy rate of segment is: " + segmentAccuracy.getAccuracyRate())
    println("There are " + segmentAccuracy.getWrong() + " errors and total expect word is " + segmentAccuracy.getTotalWords() + " when doing accuracy test.")

    println("There are " + segmentAccuracy.getErrorAnalyzer(UnknownWord).getErrorOccurTimes() + " errors because of new word.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(NER_NR).getErrorOccurTimes() + " errors because of name recognition.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(NER_NS).getErrorOccurTimes() + " errors because of place name recognition.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(ContainDisambiguate).getErrorOccurTimes() + " errors because of contain disambiguate.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(Other).getErrorOccurTimes() + " other errors")

//    printDetails(segmentAccuracy)

    Assert.assertTrue(segmentAccuracy.getAccuracyRate() > 0.94021 * 0.99)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(UnknownWord).getErrorOccurTimes() <= 23891 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(NER_NR).getErrorOccurTimes() <= 4156 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(NER_NS).getErrorOccurTimes() <= 3282 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(ContainDisambiguate).getErrorOccurTimes() <= 35317 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(Other).getErrorOccurTimes() <= 3610 * 1.05)
  }

  @Ignore
  @Test
  def should_recognize_all_the_name_entities() {
    val segmentWorker =
      SegmentEngine().getSegmentWorker(
        "separate.xingming -> true",
        "minimize.word -> true"
      )

    val segmentAccuracy = new SegmentAccuracy("test-pfr-corpus.txt", segmentWorker)
    segmentAccuracy.checkSegmentAccuracy()
    println("Accuracy rate of segment is: " + segmentAccuracy.getAccuracyRate())
    println("There are " + segmentAccuracy.getWrong() + " errors and total expect word is " + segmentAccuracy.getTotalWords() + " when doing accuracy test.")

    println("There are " + segmentAccuracy.getErrorAnalyzer(UnknownWord).getErrorOccurTimes() + " errors because of new word.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(NER_NR).getErrorOccurTimes() + " errors because of name recognition.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(NER_NS).getErrorOccurTimes() + " errors because of place name recognition.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(ContainDisambiguate).getErrorOccurTimes() + " errors because of contain disambiguate.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(Other).getErrorOccurTimes() + " other errors")

    //    printDetails(segmentAccuracy)

    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(NER_NR).getErrorOccurTimes() == 0)
  }

  @Test
  def should_be_higher_than_93_percent() {
    val segmentWorker = SegmentEngine().getSegmentWorker("separate.xingming -> true")
    val segmentAccuracy = new SegmentAccuracy("PFR-199801-utf-8.txt", segmentWorker)
    segmentAccuracy.checkSegmentAccuracy()
    println("Accuracy rate of segment is: " + segmentAccuracy.getAccuracyRate())
    println("There are " + segmentAccuracy.getWrong() + " errors and total expect word is " + segmentAccuracy.getTotalWords() + " when doing accuracy test.")

    println("There are " + segmentAccuracy.getErrorAnalyzer(UnknownWord).getErrorOccurTimes() + " errors because of new word.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(NER_NR).getErrorOccurTimes() + " errors because of name recognition.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(NER_NS).getErrorOccurTimes() + " errors because of place name recognition.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(ContainDisambiguate).getErrorOccurTimes() + " errors because of contain disambiguate.")
    println("There are " + segmentAccuracy.getErrorAnalyzer(Other).getErrorOccurTimes() + " other errors")

//    printDetails(segmentAccuracy)

    Assert.assertTrue(segmentAccuracy.getAccuracyRate() > 0.93994 * 0.99)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(UnknownWord).getErrorOccurTimes() <= 23534 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(NER_NR).getErrorOccurTimes() <= 4155 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(NER_NS).getErrorOccurTimes() <= 3036 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(ContainDisambiguate).getErrorOccurTimes() <= 36202 * 1.05)
    Assert.assertTrue(segmentAccuracy.getErrorAnalyzer(Other).getErrorOccurTimes() <= 3612 * 1.05)
  }

  private def printDetails(segmentAccuracy: SegmentAccuracy) {
    println("Possible " + segmentAccuracy.getErrorAnalyzer(UnknownWord).getWords().size + " new words")
    println("Total count: " + getWordsCount(segmentAccuracy.getErrorAnalyzer(UnknownWord)) + ", they are:")
    println(segmentAccuracy.getErrorAnalyzer(UnknownWord).getWords())
    println("Those " + segmentAccuracy.getErrorAnalyzer(ContainDisambiguate).getWords().size + " words maybe could delete from dictionary")
    println("Total count: " + getWordsCount(segmentAccuracy.getErrorAnalyzer(ContainDisambiguate)) + ", they are:")
    println(segmentAccuracy.getErrorAnalyzer(ContainDisambiguate).getWords())
  }

  private def getWordsCount(errorAnalyzer: ErrorAnalyzer): Int = {
    val words = errorAnalyzer.getWords()
    var count = 0
    for (word <- words.keys) {
      count += words(word)
    }
    return count
  }
}
