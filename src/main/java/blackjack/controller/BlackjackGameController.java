package blackjack.controller;

import blackjack.domain.BlackjackGame;
import blackjack.domain.user.User;
import blackjack.view.InputView;
import blackjack.view.OutputView;

import java.util.List;
import java.util.stream.Collectors;

import static blackjack.domain.user.Status.PLAYING;

public class BlackjackGameController {
    private static final int DEALER_MINIMUM_SCORE = 16;

    public static void start() {
        BlackjackGame blackjackGame = startGameAndFirstDraw();
        printFirstDrawInformation(blackjackGame);
        OutputView.println();

        processUserRound(blackjackGame);
        OutputView.println();

        processDealerRound(blackjackGame);

        createResultAndPrint(blackjackGame);
    }

    private static void printFirstDrawInformation(BlackjackGame blackjackGame) {
        OutputView.printDrawMessage(blackjackGame.getUserNames());
        OutputView.println();

        printFirstDrawCards(blackjackGame);
    }

    private static BlackjackGame startGameAndFirstDraw() {
        List<User> users = InputView.askPlayersName()
                .stream()
                .map(User::new)
                .collect(Collectors.toList());

        return BlackjackGame.createAndFirstDraw(users);
    }

    private static void printFirstDrawCards(BlackjackGame blackjackGame) {
        OutputView.printDealerFirstCard(blackjackGame.getDealer().getFirstCard());
        blackjackGame.getUsers().forEach(OutputView::printCardList);
    }

    private static void processUserRound(BlackjackGame blackjackGame) {
        while (blackjackGame.isExistPlayingUser()) {
            User currentUser = blackjackGame.findFirstUserByStatus(PLAYING).get();
            userDrawOrStop(blackjackGame, currentUser, InputView.askMoreDraw(currentUser.getName()));
            printUserCurrentCards(currentUser);
        }
    }

    private static void printUserCurrentCards(User currentUser) {
        OutputView.printCardList(currentUser);
    }

    private static void userDrawOrStop(BlackjackGame blackjackGame, User currentUser, Boolean isContinue) {
        if (isContinue) {
            currentUser.drawCard(blackjackGame.draw());
            return;
        }
        currentUser.stopUser();
    }

    private static void processDealerRound(BlackjackGame blackjackGame) {
        while (blackjackGame.getDealer().calculateScore() <= DEALER_MINIMUM_SCORE) {
            blackjackGame.getDealer().drawCard(blackjackGame.draw());
            OutputView.printDealerMoreDrawMessage();
        }
    }

    private static void createResultAndPrint(BlackjackGame blackjackGame) {
        OutputView.printScoreBoard(blackjackGame.createScoreBoard(), blackjackGame.getDealer());
    }
}
