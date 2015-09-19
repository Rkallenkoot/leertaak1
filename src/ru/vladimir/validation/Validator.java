package ru.vladimir.validation;

import org.jdom2.Element;
import ru.vladimir.model.Measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Validator {

    // massieve HashMap met laatste 30 values per STN
    private ConcurrentHashMap<String, ArrayList<Measurement>> measurements;

    public static List<Element> validateWeatherData(List<Element> elements) {
        for (Element el : elements) {
            String missingChild = "";
            for (Element e : el.getChildren()) {
                if (e.getText().equals("")) {
                    missingChild = e.getName();
                }

                System.out.println(e.getName() + " - " + e.getText());
            }
            if (missingChild.length() > 0) {
                System.out.println("MISSING CHILD: " + missingChild);
            }
            // Extrapoleer missing child op basis van 30 laatste waarden

            // Extrapoleer zo nodig temperatuur
            // ?? next
        }

        return elements;
    }


}
