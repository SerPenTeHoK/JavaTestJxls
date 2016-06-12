package org.jxls.demo;

import org.jxls.common.Context;
import org.jxls.template.SimpleExporter;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created by SerP on 11.06.2016.
 */
public class SimpleExporterDemoNew {
    static Logger logger = LoggerFactory.getLogger(GridCommandDemo.class);
    private static String mainTemplate = "simple_export_template3.xlsx";

    public static void makeReport(String template, Map obj) {
        if (obj.isEmpty() || obj.get("Values") == null) {
            return;
        }
        List listMapValues = (List) obj.get("Values");
        StringBuilder headersStr = new StringBuilder();
        // Вывод всех значений
        if (template.isEmpty() && obj.get("Headers") == null) {
            List headersList = new ArrayList();
            // Строим список заголовков и название по первому элементу списка значений
            for (Object val : ((Map) listMapValues.get(0)).keySet()) {
                headersStr.append(val).append(",");
                headersList.add(val);
            }
            try (OutputStream os1 = new FileOutputStream("target/simple_export_output_all.xls")) {
                SimpleExporter exporter = new SimpleExporter();
                exporter.gridExport(headersList, listMapValues, headersStr.toString(), os1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // Вывод значений указанных в заголовке
        else if (template.isEmpty() && obj.get("Headers") != null) {
            // Строим название заголовков по списку заголовков
            List headersList = (List) obj.get("Headers");
            for (Object val : headersList) {
                headersStr.append(val).append(",");
            }
            try (OutputStream os1 = new FileOutputStream("target/simple_export_output_all_headers.xls")) {
                SimpleExporter exporter = new SimpleExporter();
                exporter.gridExport(headersList, listMapValues, headersStr.toString(), os1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // если есть шаблон и нет заголовков
        else if (!template.isEmpty() && obj.get("Headers") == null) {
            try (InputStream is = SimpleExporterDemo.class.getResourceAsStream(template)) {
                try (OutputStream os3 = new FileOutputStream("target/simple_export_output_all_template.xlsx")) {
                    Context context = new Context();
                    context.putVar("data", listMapValues);
                    JxlsHelper.getInstance().processTemplate(is, os3, context);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // если есть шаблон и есть заголовки
        else if (!template.isEmpty() && obj.get("Headers") != null) {
            try (InputStream is = SimpleExporterDemo.class.getResourceAsStream(template)) {
                try (OutputStream os3 = new FileOutputStream("target/simple_export_output_all_template_headers.xlsx")) {
                    Context context = new Context();
                    List headersList = (List) obj.get("Headers");
                    context.putVar("headers", headersList);
                    // переколбасить данные для вывода

                    context.putVar("data", listMapValues);
                    JxlsHelper.getInstance().processTemplate(is, os3, context);
                    //JxlsHelper.getInstance().processGridTemplateAtCell(is, os3, context, headersStr.toString(), "Sheet1!A1");

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

/*
        try (OutputStream os1 = new FileOutputStream("target/simple_export_output1.xls")) {
            SimpleExporter exporter = new SimpleExporter();
            // Вывод всего подряд в зависимости от внутреннего формата
            exporter.gridExport(headersList, listMapValues, headersStr.toString(), os1);
            try (InputStream is = SimpleExporterDemo.class.getResourceAsStream(template)) {
                try (OutputStream os3 = new FileOutputStream("target/simple_export_output3.xlsx")) {
                    Context context = new Context();
                    //context.putVar("headers", headersList);
                    context.putVar("data", listMapValues);
                    JxlsHelper.getInstance().processTemplate(is, os3, context);
                    //JxlsHelper.getInstance().processGridTemplateAtCell(is, os3, context, headersStr.toString(), "Sheet1!A1");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }

    public static void main(String[] args) throws ParseException, IOException {
        logger.info("Running Simple Export demo");
        Map value1 = new LinkedHashMap();
        value1.put("Name", "Alex1");
        value1.put("Payment", 5000);
        value1.put("Date", new Date());
        value1.put("Ololo", new Date());
        value1.put("POLE", 1);
        value1.put("Руслиш", "Руслиш1");

        Map value2 = new LinkedHashMap();
        value2.put("Name", "Alex2");
        value2.put("Date", new Date());
        value2.put("Payment", 9000);
        //value2.put("Ololo", new Date());
        value2.put("POLE", 2);
        value2.put("Руслиш", "Руслиш2");

        List listMapValues = new ArrayList();
        listMapValues.add(value1);
        listMapValues.add(value2);

        Map obj = new HashMap();
        obj.put("Values", listMapValues);

        makeReport("", obj);
        makeReport(mainTemplate, obj);

        List headersList = Arrays.asList("Name", "Date", "POLE", "Ololo");
        obj.put("Headers", headersList);
        makeReport("", obj);
        makeReport(mainTemplate, obj);
    }
}
