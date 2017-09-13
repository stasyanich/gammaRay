import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label xmlFileName;
    @FXML
    private Label avrGammaRay;

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
        try {
            File file = fileChooser.showOpenDialog(Main.getStage());
            if (file != null) {
                parseXmlFile(file);
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Выберите XML файл!!");
            alert.showAndWait();
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
//                System.out.println(strParse[2]);
                Float item = Float.parseFloat(strParse[2]);
                sum += item;
                count++;
            }

        } catch (Exception e) { // FIXME: 12.09.2017 дописать exeptions
            avrGammaRay.setText("ERROR");

            e.printStackTrace();
        }
        String result = formatFloat(sum / count);
        avrGammaRay.setText(result);

    }

    private String formatFloat(Float f) {
        DecimalFormatSymbols custom = new DecimalFormatSymbols(Locale.ENGLISH);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(custom);
        return decimalFormat.format(f);
    }
}
