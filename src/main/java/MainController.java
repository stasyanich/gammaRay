import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Label xmlFileName;
    @FXML private Label avrGammaRay;

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
        try {
            File file = fileChooser.showOpenDialog(Main.getStage());
            if (file != null) {
                parseXmlFile(file);
            }
        } catch (NullPointerException e) {
            xmlAlert("Выберите XML файл!!");
            e.getStackTrace();
        }
    }

    private void parseXmlFile(File file) {
        int count = 0;
        Float sum = 0.0f;
        xmlFileName.setText(file.getName());
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList logsList = document.getElementsByTagName("data");

            for (int i = 0; i < logsList.getLength(); i++) {
                Node node = logsList.item(i);
                String[] strParse = node.getTextContent().split(",");
                Float item = Float.parseFloat(strParse[2]);
                sum += item;
                count++;
            }

            String result = formatFloat(sum / count);
            avrGammaRay.setText(result);

        } catch (ParserConfigurationException e) {
            xmlAlert("ParserConfigurationException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            xmlAlert("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (SAXException e) {
            xmlAlert("SAXException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            xmlAlert("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
    * обрезает значение до двух знаков после точки
     */
    private String formatFloat(Float f) {
        DecimalFormatSymbols custom = new DecimalFormatSymbols(Locale.ENGLISH);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(custom);
        return decimalFormat.format(f);
    }

    /*
    * вывод окна с описанием ошибки
     */
    private void xmlAlert(String errorText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(errorText);
        avrGammaRay.setText("ERROR");
        alert.showAndWait();
    }
}
