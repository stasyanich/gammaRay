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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private Label xmlFileName;
    @FXML private Label avrGammaRay;

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML void handleLoad(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
        try {
            File file = fileChooser.showOpenDialog(Main.getStage());
            if (file != null){
                parseXmlFile(file);
            }
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Выберите XML файл!!");
            alert.showAndWait();
        }
    }

    private void parseXmlFile(File file){
        file.getName();
        xmlFileName.setText(file.getName());
        avrGammaRay.setText("36525.22");
        int count = 0;
        Float sum = 0.0f;
        ArrayList<Float> result = new ArrayList<Float>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Node root = document.getDocumentElement();

            NodeList logsList = root.getChildNodes();
            for (int i = 0; i < logsList.getLength(); i++){
                Node node = logsList.item(i);
                if (node.getNodeName().contentEquals("log")){
                    NodeList logList = node.getChildNodes();
                    for (int j = 0; j < logList.getLength();j++){
                        Node n = logList.item(j);
                        if (n.getNodeName().contentEquals("logData")){
                            NodeList logDataList = n.getChildNodes();
                            for (int k = 0; k < logDataList.getLength(); k++){
                                if (n.getNodeType()==Node.ELEMENT_NODE){
                                    Element element = (Element) n;
                                    String[] strParse = element.getElementsByTagName("data").item(k).getTextContent().split(",");
                                    for (String s: strParse){
                                        System.out.print(s + " ");
                                    }
                                    System.out.println();
                                    count++;
                            }

//                                Float radiation = Float.parseFloat(strParse[2]);
//                                sum +=radiation;
//                                result.add(radiation);



                            }
                            System.out.println(count);
                        }
                    }
                }
            }
            System.out.println(sum);
            System.out.println(document.getDocumentElement().getNodeName());
            System.out.println(result);
        }catch (Exception e){ // FIXME: 12.09.2017 дописать exeptions
            e.printStackTrace();
        }

    }
}
