package cliniccaresystem.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cliniccaresystem.Main;
import cliniccaresystem.model.Gender;
import cliniccaresystem.model.MailingAddress;
import cliniccaresystem.model.Patient;
import cliniccaresystem.model.Test;
import cliniccaresystem.model.TestSummary;
import cliniccaresystem.model.VisitInformation;
import cliniccaresystem.viewmodel.AdminHomepageViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

public class AdminHomepageCodeBehind {

	@FXML
	private TableView<ObservableList<String>> searchResultTableView;

	@FXML
	private TextArea searchQueryTextArea;

	@FXML
	private Button searchQueryButton;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private Button searchDatesButton;

	@FXML
	private Label adminInfoLabel;

	@FXML
	private Label invalidQueryLabel;
	
	@FXML
    private TableView<VisitInformation> dateSearchResultTableView;

    @FXML
    private TableColumn<VisitInformation, String> visitDateColumn;

    @FXML
    private TableColumn<VisitInformation, String> patientIdColumn;

    @FXML
    private TableColumn<VisitInformation, String> patientFirstNameColumn;

    @FXML
    private TableColumn<VisitInformation, String> patientLastNameColumn;

    @FXML
    private TableColumn<VisitInformation, String> doctorFirstNameColumn;

    @FXML
    private TableColumn<VisitInformation, String> doctorLastNameColumn;

    @FXML
    private TableColumn<VisitInformation, String> nurseFirstNameColumn;

    @FXML
    private TableColumn<VisitInformation, String> nurseLastNameColumn;

    @FXML
    private TableColumn<VisitInformation, String> diagnosisColumn;
    
    @FXML
    private TableView<TestSummary> testTableView;

    @FXML
    private TableColumn<TestSummary, String> testNameColumn;

    @FXML
    private TableColumn<TestSummary, String> isAbnormalColumn;
    
    @FXML
    private TableColumn<TestSummary, String> testResultColumn;
	
	private AdminHomepageViewModel viewmodel;

	public AdminHomepageCodeBehind() {
		this.viewmodel = new AdminHomepageViewModel();
	}

	@FXML
	void initialize() {
		this.bindToViewModel();
		this.initializeQueryResultTableViewForDateSearch();
		this.initializeTestTable();
		this.testTableView.setDisable(true);
		this.searchResultTableView.setVisible(true);
		this.dateSearchResultTableView.setVisible(false);
		this.setupChangeListener();
	}

	private void bindToViewModel() {
		this.searchResultTableView.setItems(this.viewmodel.queryResultListProperty());
		this.dateSearchResultTableView.setItems(this.viewmodel.dateSearchResultListProperty());
		this.testTableView.setItems(this.viewmodel.testListProperty());

		this.startDatePicker.valueProperty().bindBidirectional(this.viewmodel.startDateProperty());
		this.endDatePicker.valueProperty().bindBidirectional(this.viewmodel.endDateProperty());
		this.adminInfoLabel.textProperty().bindBidirectional(this.viewmodel.adminInfoProperty());
		this.searchQueryTextArea.textProperty().bindBidirectional(this.viewmodel.searchQueryProperty());
	}
	
	private void setupChangeListener() {
		this.dateSearchResultTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.viewmodel.showTests(newValue);
			}
		});
	}

	@FXML
	void onSearchDates(ActionEvent event) {
		this.clearTableData();
		this.searchResultTableView.setVisible(false);
		this.dateSearchResultTableView.setVisible(true);
		this.testTableView.setDisable(false);

		this.viewmodel.searchDates();
		
		this.dateSearchResultTableView.setItems(this.viewmodel.dateSearchResultListProperty());
		
	}
	
	private void initializeQueryResultTableViewForDateSearch() {
		this.visitDateColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("visitDate"));
		this.patientIdColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("patientId"));
		this.patientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("patientFirstName"));
		this.patientLastNameColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("patientLastName"));
		this.doctorFirstNameColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("doctorFirstName"));
		this.doctorLastNameColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("doctorLastName"));
		this.nurseFirstNameColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("nurseFirstName"));
		this.nurseLastNameColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("nurseLastName"));
		this.diagnosisColumn.setCellValueFactory(new PropertyValueFactory<VisitInformation, String>("finalDiagnosis"));
	}
	
	private void initializeTestTable() {
		this.testNameColumn.setCellValueFactory(new PropertyValueFactory<TestSummary, String>("testName"));
		this.isAbnormalColumn.setCellValueFactory(new PropertyValueFactory<TestSummary, String>("isAbnormal"));
		this.testResultColumn.setCellValueFactory(new PropertyValueFactory<TestSummary, String>("result"));
	}


	@FXML
	void onSearchQuery(ActionEvent event) {
		this.clearTableData();
		this.searchResultTableView.setVisible(true);
		this.dateSearchResultTableView.setVisible(false);
		this.testTableView.setDisable(true);
		
		var resultMap = this.viewmodel.sendSearchQuery();
		
		if (resultMap == null) {
			this.invalidQueryLabel.setVisible(true);
		} else {
			this.addColumnsToTable(resultMap);
			this.viewmodel.addDataToTable(resultMap, this.searchResultTableView.getColumns().get(0).getText());
			this.searchResultTableView.setItems(this.viewmodel.queryResultListProperty());
		}
	}

	private void clearTableData() {
		this.invalidQueryLabel.setVisible(false);
		this.searchResultTableView.getItems().clear();
		this.searchResultTableView.getColumns().clear();
		this.viewmodel.clearData();
	}

	private void addColumnsToTable(HashMap<String, ArrayList<String>> map) {
		
		for(int i=0 ; i < map.size(); i++){
			
            final int j = i;                
            TableColumn col = new TableColumn((String) map.keySet().toArray()[i]);
            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
                    return new SimpleStringProperty(param.getValue().get(j).toString());                        
                }                    
            });

            this.searchResultTableView.getColumns().addAll(col); 
        }
	}
	
    @FXML
    void onLogout(ActionEvent event) {
    	this.viewmodel.logout();
    	
    	Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
    	try {
			Main.changeScene(currentStage, Main.LOGIN_PAGE_PATH, Main.LOGIN_PAGE_TITLE);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
