package ro.deathy.langserver;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private static final LanguageDetector detector = LanguageDetectorBuilder.fromAllLanguages().build();

    @RequestMapping(value = "/detect-language", method = RequestMethod.GET)
    public DetectionResponse detectLanguage(@RequestParam("q") String query) {
        final var languageDoubleSortedMap = detector.computeLanguageConfidenceValues(query);
        List<DetectionResponse.Detection> detected = new ArrayList<>(languageDoubleSortedMap.size());
        for (Map.Entry<Language, Double> languageDoubleEntry : languageDoubleSortedMap.entrySet()) {
            detected.add(new DetectionResponse.Detection(languageDoubleEntry.getKey().toString(), languageDoubleEntry.getValue().floatValue()));
        }
        return new DetectionResponse(query, detected);
    }

}
