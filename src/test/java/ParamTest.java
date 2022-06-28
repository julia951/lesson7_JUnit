import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;
import java.util.stream.Stream;
import static java.util.Arrays.asList;

import com.codeborne.selenide.CollectionCondition;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.*;

public class ParamTest {

    @ParameterizedTest(name = "При поиске слова - {0} отображаются статьи про {0}")
    @ValueSource(strings = {"qa", "qc", "testing"})
    void testGoogleSearch(String searchItem) {
        open("https://www.google.com/");
        $(".gLFyf.gsfi").setValue(searchItem);
        $(".gNO89b").pressEnter();
        $$(".jtfYYd").find((text(searchItem))).shouldBe(visible);
    }

    @CsvSource(value = {
            "qa, самое широкое из всех понятий и представляет собой совокупность мероприятий",
            "qc, це процес знаходження помилок в продукті",
            "testing, delegation would like to urge those States"})
    @ParameterizedTest(name = "При поиске слова - {0} отображаются статьи про {1}")
    void testGoogleSearchCSV(String searchItem) {
        open("https://www.google.com/");
        $(".gLFyf.gsfi").setValue(searchItem);
        $(".gNO89b").pressEnter();
        $$(".jtfYYd").find((text(searchItem))).shouldBe(visible);
    }

    @CsvFileSource(resources = "testData/forGoogle")
    @ParameterizedTest(name = "При поиске слова - {0} отображаются статьи про {1}")
    void testGoogleSearchCSVFile(String searchItem) {
        open("https://www.google.com/");
        $(".gLFyf.gsfi").setValue(searchItem);
        $(".gNO89b").pressEnter();
        $$(".jtfYYd").find((text(searchItem))).shouldBe(visible);
    }

    static Stream<Arguments>googleItemSearch(){
        return Stream.of(
                Arguments.of("qa", asList("Quality assurance")),
                Arguments.of("qc", asList("Quality Control")),
                Arguments.of("testing", asList("Software testing")
                ));
    }
    @MethodSource(value = "googleItemSearch")
    @ParameterizedTest(name = "При поиске слова - {0} отображаются статьи про {1}")
    void testGoogleSearchStream(String searchItem, List<String> expectedResult) {
        open("https://www.google.com/");
        $(".gLFyf.gsfi").setValue(searchItem);
        $(".gNO89b").pressEnter();
        $$(".jtfYYd").shouldHave(CollectionCondition.texts(expectedResult));
    }

    @EnumSource(enumExamples.class)
    @ParameterizedTest
    void enumTest(enumExamples enumExamples) {
        open("https://www.google.com/");
        $(".gLFyf.gsfi").setValue(enumExamples.desc);
        $(".gNO89b").pressEnter();
        $$(".jtfYYd").shouldHave(CollectionCondition.texts(enumExamples.desc));
    }

}
