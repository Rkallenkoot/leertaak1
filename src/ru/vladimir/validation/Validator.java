package ru.vladimir.validation;

import org.jdom2.Element;
import ru.vladimir.model.Measurement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Validator {

    private static final int MEASUREMENT_LIST_SIZE = 30;

    // Map met laatste values per Station
    private Map<Integer, List<Measurement>> measurements;

    public Validator() {
        // TODO: We zouden de laatste 30 values uit DB kunnen halen
        measurements = new HashMap<>();
    }

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


    public Measurement validateMeasurement(Measurement mes) {
        List<Measurement> list =
                measurements.get(mes.getStn()) != null ? measurements.get(mes.getStn()) : new ArrayList<Measurement>();

        /**
         * Check if Extrapolation is needed
         */
        if (mes.getDewp() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getDewp();
                }
                val = sum / size;
            }
            mes.setDewp(val);
        } else if (mes.getStp() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getStp();
                }
                val = sum / size;
            }
            mes.setStp(val);
        } else if (mes.getSlp() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getSlp();
                }
                val = sum / size;
            }
            mes.setSlp(val);
        } else if (mes.getVisib() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getVisib();
                }
                val = sum / size;
            }
            mes.setVisib(val);
        } else if (mes.getWdsp() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getWdsp();
                }
                val = sum / size;
            }
            mes.setWdsp(val);
        } else if (mes.getPrcp() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getPrcp();
                }
                val = sum / size;
            }
            mes.setPrcp(val);
        } else if (mes.getSndp() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getSndp();
                }
                val = sum / size;
            }
            mes.setSndp(val);
        } else if (mes.getCldc() == Float.MAX_VALUE) {
            float val = 0;
            float sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getCldc();
                }
                val = sum / size;
            }
            mes.setCldc(val);
        } else if (mes.getWnddir() == Short.MAX_VALUE) {
            int val = 0;
            int sum = 0;
            short size = (short) list.size();
            if (size != 0) {
                for (Measurement m : list) {
                    sum += m.getWnddir();
                }
                val = ((short) sum / size);
            }
            mes.setWnddir((short) val);
        }
        // TODO: frshtt kunnen we berekenen

        float sum = 0;
        short size = (short) list.size();
        if (size != 0) {
            for (Measurement m : list) {
                sum += m.getTemp();
            }
            float temp = sum / size;
            float twentyPercent = Math.abs(temp * 0.2f);
            float difference = Math.abs(temp - mes.getTemp());
            if (difference > twentyPercent) {
                // Extrapoleren bij verschil van 20%
                mes.setTemp(temp);
            }
        }
        /**
         * End of checking extrapolation
         * Measurement should contain correct data here
         */


        /**
         * Push measurement in the list
         * this list should not be bigger than MEASUREMENT_LIST_SIZE
         * if that's the case we remove the first element before adding a new element
         *
         * These values will be used to extrapolate
         */
        if (list.size() >= MEASUREMENT_LIST_SIZE) {
            list.remove(0);
        }
        list.add(mes);

        /**
         * Update the Map measurementsj
         */
        measurements.put(mes.getStn(), list);
        // Return the measurement so we can persist correct values to DB
        return mes;
    }
}
