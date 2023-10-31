import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.BookModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class FileTest {

    private ClassLoader cl = FileTest.class.getClassLoader();
    ObjectMapper mapper = new ObjectMapper();


    @Test
    void xmlTest() throws Exception{
        try (InputStream stream = cl.getResourceAsStream("HW_11.zip");
             ZipInputStream zis = new ZipInputStream(stream)){

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                if (entry.getName().contains("DEBUGGING.xlsx")){
                    XLS xls = new XLS(zis);
                    Assertions.assertEquals("Ctrl + Shift + F8",
                            xls.excel.getSheetAt(0)
                                    .getRow(4)
                                    .getCell(1)
                                    .getStringCellValue());
                }
            }
        }
    }
    @Test
    void csvTest() throws Exception {
     try (InputStream stream = cl.getResourceAsStream("HW_11.zip");
     ZipInputStream zis = new ZipInputStream(stream)){
         ZipEntry entry;
         while ((entry = zis.getNextEntry()) != null){
             if (entry.getName().contains("month.csv")){
                 CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                 List<String[]> content = csvReader.readAll();
                 Assertions.assertEquals(4,content.size());
                 Assertions.assertArrayEquals(new String[]{"Январь","01"}, content.get(0));
                 Assertions.assertArrayEquals(new String[]{"Март","03"}, content.get(2));
             }
         }
     }
    }


    @Test
    void pdfTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("HW_11.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().contains("IntelliJIDEA_ReferenceCard.pdf")) {
                    PDF pdf = new PDF(zis);

                    Assertions.assertEquals(2, pdf.numberOfPages);
                    Assertions.assertEquals("Adobe PDF Library 15.0",pdf.producer);
                }

            }
        }
    }


    @Test
    void jsonTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("book.json");
             Reader reader = new InputStreamReader(stream)) {
            BookModel bookModel = mapper.readValue(reader, BookModel.class);

            Assertions.assertEquals("Effective Java", bookModel.getBookTitle());
            Assertions.assertEquals( "second", bookModel.getBookChapters().get(1));
        }
    }

}
