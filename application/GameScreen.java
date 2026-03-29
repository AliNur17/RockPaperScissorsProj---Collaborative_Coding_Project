package application;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameScreen implements AppScreen {

    private final ScreenManager manager;
    private final GameData gameData;

    private int currentRound = 1;
    private int humanWins = 0;
    private int computerWins = 0;
    private int ties = 0;

    private Label roundCounter;
    private Label humanWinsLabel;
    private Label computerWinsLabel;
    private Label tiesLabel;
    
    private StackPane computerRockCard;
    private StackPane computerPaperCard;
    private StackPane computerScissorsCard;

    private Label computerRockLabel;
    private Label computerPaperLabel;
    private Label computerScissorsLabel;
    
    private Button rockButton;
    private Button paperButton;
    private Button scissorsButton;
    
    private boolean userChoiceLocked = false;

    private HBox barcodeRow;
    private boolean waitingForProceed = false;

    public GameScreen(ScreenManager manager, GameData gameData) {
        this.manager = manager;
        this.gameData = gameData;
    }

    @Override
    public Parent getView() {

        BorderPane content = new BorderPane();
        content.setId("gameRoot");
        content.setMinSize(600, 400);
        content.setMaxSize(600, 400);

        VBox leftPanel = createStatsPanel();
        VBox centerArea = createMainGameArea();

        content.setLeft(leftPanel);
        content.setCenter(centerArea);

        Rectangle divider = new Rectangle(5, 400);
        divider.setFill(Color.BLACK);
        divider.setOpacity(0.2);
        divider.setTranslateX(-(600 / 2) + 145 + 15 + 10);

        StackPane root = new StackPane(content, divider);

        return root;
    }

    private VBox createStatsPanel() {
        Label statsTitle = new Label("Statistics");
        statsTitle.setId("statsTitle");

        humanWinsLabel = new Label("Human Wins: " + humanWins);
        computerWinsLabel = new Label("Computer Wins: " + computerWins);
        tiesLabel = new Label("Ties: " + ties);

        humanWinsLabel.getStyleClass().add("statsLabel");
        computerWinsLabel.getStyleClass().add("statsLabel");
        tiesLabel.getStyleClass().add("statsLabel");

        VBox statsBox = new VBox(6, humanWinsLabel, computerWinsLabel, tiesLabel);
        statsBox.setId("statsBox");

        Label receiptTitle = new Label("Human");
        receiptTitle.getStyleClass().add("receiptHeading");

        Label humanChoice = new Label("Chooses:  Paper"); // EDIT THIS *************************
        humanChoice.getStyleClass().add("receiptTextSmall");

        Label computerHeader = new Label("Computer");
        computerHeader.getStyleClass().add("receiptHeading");

        Label prediction = new Label("Predicted human choice:  Paper"); // EDIT THIS *********************
        prediction.getStyleClass().add("receiptTextSmall");

        Label decision = new Label("Therefore computer chooses:  Scissors"); // EDIT THIS **********************
        decision.getStyleClass().add("receiptTextSmall");

        Label winner = new Label("The winner:   Computer"); // EDIT THIS ************************
        winner.getStyleClass().add("receiptWinner");

        barcodeRow = new HBox();
        barcodeRow.setId("barcodeRow");
        barcodeRow.setAlignment(Pos.CENTER);

        Region leftBars = new Region();
        leftBars.getStyleClass().add("barcode");

        Label proceedText = new Label("PROCEED");
        proceedText.getStyleClass().add("barcodeText");

        Region rightBars = new Region();
        rightBars.getStyleClass().add("barcode");

        barcodeRow.getChildren().addAll(leftBars, proceedText, rightBars);
        barcodeRow.setVisible(false);
        barcodeRow.setManaged(false);
        barcodeRow.setDisable(true);

        barcodeRow.setOnMouseClicked(e -> handleProceed());

        VBox receiptBox = new VBox(
                5,
                receiptTitle,
                humanChoice,
                computerHeader,
                prediction,
                decision,
                winner,
                barcodeRow
        );
        receiptBox.setId("receiptBox");
        receiptBox.setMinHeight(190);
        receiptBox.setMaxHeight(190);

        Button exitButton = new Button("Exit to Main");
        exitButton.setId("exitButton");
        exitButton.setOnAction(e -> {
            resetGame();
            manager.show(Main.MENU);
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox leftPanel = new VBox(14, statsTitle, statsBox, receiptBox, spacer, exitButton);
        leftPanel.setId("statsPanel");
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setPadding(new Insets(18, 14, 18, 14));
        leftPanel.setPrefWidth(145);

        return leftPanel;
    }

    private VBox createMainGameArea() {
        Label computerLabel = new Label("Computer");
        computerLabel.setId("computerLabel");

        computerRockCard = createComputerCard("Rock");
        computerPaperCard = createComputerCard("Paper");
        computerScissorsCard = createComputerCard("Scissors");

        HBox computerBoxes = new HBox(
                34,
                computerRockCard,
                computerPaperCard,
                computerScissorsCard
        );
        computerBoxes.setId("computerBoxes");
        computerBoxes.setAlignment(Pos.CENTER);

        roundCounter = new Label("ROUND: " + currentRound); // EDIT THIS **************************
        roundCounter.setId("roundCounter");

        rockButton = new Button("Rock");
        paperButton = new Button("Paper");
        scissorsButton = new Button("Scissors");
        
        rockButton.getStyleClass().add("moveButton");
        paperButton.getStyleClass().add("moveButton");
        scissorsButton.getStyleClass().add("moveButton");

        rockButton.setOnAction(e -> handleUserChoice("Rock"));
        paperButton.setOnAction(e -> handleUserChoice("Paper"));
        scissorsButton.setOnAction(e -> handleUserChoice("Scissors"));
        
        HBox moveButtons = new HBox(28, rockButton, paperButton, scissorsButton);
        moveButtons.setId("moveButtons");
        moveButtons.setAlignment(Pos.CENTER);

        Label instruction = new Label("Select Rock, Paper, or Scissors");
        instruction.setId("instructionLabel");

        Label humanLabel = new Label("Human");
        humanLabel.setId("humanLabel");

        VBox centerArea = new VBox(
                22,
                computerLabel,
                computerBoxes,
                roundCounter,
                moveButtons,
                instruction,
                humanLabel
        );
        centerArea.setId("mainGameArea");
        centerArea.setAlignment(Pos.CENTER);
        centerArea.setPadding(new Insets(24, 20, 20, 20));

        return centerArea;
    }
    
    private void handleUserChoice(String choice) {
        if (userChoiceLocked) {
            return;
        }

        userChoiceLocked = true;
        lockUserButtons();

        showSelectedUserCard(choice);
        showMatchingComputerCard(choice);
        showReceiptAction();
    }

    private void lockUserButtons() {
        rockButton.setDisable(true);
        paperButton.setDisable(true);
        scissorsButton.setDisable(true);
    }

    private void unlockUserButtons() {
        rockButton.setDisable(false);
        paperButton.setDisable(false);
        scissorsButton.setDisable(false);
    }

    private void showSelectedUserCard(String choice) { // EDIT THIS ************************
        clearUserSelection();

        rockButton.getStyleClass().add("moveButtonBlurred");
        paperButton.getStyleClass().add("moveButtonBlurred");
        scissorsButton.getStyleClass().add("moveButtonBlurred");

        if ("Rock".equals(choice)) {
            rockButton.getStyleClass().remove("moveButtonBlurred");
            rockButton.getStyleClass().add("moveButtonSelected");
        } else if ("Paper".equals(choice)) {
            paperButton.getStyleClass().remove("moveButtonBlurred");
            paperButton.getStyleClass().add("moveButtonSelected");
        } else if ("Scissors".equals(choice)) {
            scissorsButton.getStyleClass().remove("moveButtonBlurred");
            scissorsButton.getStyleClass().add("moveButtonSelected");
        }
    }

    private void clearUserSelection() {
        rockButton.getStyleClass().removeAll("moveButtonSelected", "moveButtonBlurred");
        paperButton.getStyleClass().removeAll("moveButtonSelected", "moveButtonBlurred");
        scissorsButton.getStyleClass().removeAll("moveButtonSelected", "moveButtonBlurred");
    }

    private void showMatchingComputerCard(String choice) { // EDIT THIS (Only Debugging) ************************
        if ("Rock".equals(choice)) {
            showComputerRock();
        } else if ("Paper".equals(choice)) {
            showComputerPaper();
        } else if ("Scissors".equals(choice)) {
            showComputerScissors();
        }
    }

    private StackPane createComputerCard(String text) {
        Region box = new Region();
        box.getStyleClass().add("computerBox");

        Label label = new Label(text);
        label.getStyleClass().add("computerCardLabel");
        label.setVisible(false);
        label.setManaged(false);

        if (text.equals("Rock")) {
            computerRockLabel = label;
        } else if (text.equals("Paper")) {
            computerPaperLabel = label;
        } else if (text.equals("Scissors")) {
            computerScissorsLabel = label;
        }

        StackPane card = new StackPane(box, label);
        card.getStyleClass().add("computerCard");
        card.setPrefSize(60, 40);

        return card;
    }

    private void resetComputerCards() {
        resetComputerCard(computerRockCard, computerRockLabel);
        resetComputerCard(computerPaperCard, computerPaperLabel);
        resetComputerCard(computerScissorsCard, computerScissorsLabel);
    }

    private void resetComputerCard(StackPane card, Label label) {
        card.getStyleClass().remove("computerCardSelected");
        label.setVisible(false);
        label.setManaged(false);
    }

    private void revealComputerCard(StackPane card, Label label) {
        resetComputerCards();
        card.getStyleClass().add("computerCardSelected");
        label.setVisible(true);
        label.setManaged(true);
    }

    private void showComputerRock() {
        revealComputerCard(computerRockCard, computerRockLabel);
    }

    private void showComputerPaper() {
        revealComputerCard(computerPaperCard, computerPaperLabel);
    }

    private void showComputerScissors() {
        revealComputerCard(computerScissorsCard, computerScissorsLabel);
    }

    private void showReceiptAction() {
        if (waitingForProceed) {
            return;
        }

        waitingForProceed = true;
        barcodeRow.setVisible(false);
        barcodeRow.setManaged(false);
        barcodeRow.setDisable(true);

        PauseTransition delay = new PauseTransition(Duration.millis(500));
        delay.setOnFinished(e -> {
            barcodeRow.setVisible(true);
            barcodeRow.setManaged(true);
            barcodeRow.setDisable(false);
        });
        delay.play();
    }

    private void handleProceed() {
        if (!waitingForProceed || barcodeRow.isDisable()) {
            return;
        }

        if (currentRound < gameData.getRounds()) {
            currentRound++;
            roundCounter.setText("ROUND: " + currentRound);
        }

        waitingForProceed = false;
        clearUI();
    }

    private void resetGame() {
        currentRound = 1;
        humanWins = 0;
        computerWins = 0;
        ties = 0;
        waitingForProceed = false;

        roundCounter.setText("ROUND: 1");
        humanWinsLabel.setText("Human Wins: 0");
        computerWinsLabel.setText("Computer Wins: 0");
        tiesLabel.setText("Ties: 0");

        clearUI();
    }
    
    private void clearUI()
    {
    	barcodeRow.setVisible(false);
        barcodeRow.setManaged(false);
        barcodeRow.setDisable(true);
        
        userChoiceLocked = false;
        unlockUserButtons();
        clearUserSelection();
        resetComputerCards();
    }
}