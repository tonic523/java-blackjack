package blackjack.domain;

import static blackjack.view.OutputView.*;

import java.util.List;
import java.util.stream.Collectors;

import blackjack.domain.card.Deck;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Player;
import blackjack.domain.player.Players;
import blackjack.dto.PlayerDto;
import blackjack.view.InputView;

public class BlackjackGame {

    private final Deck deck;
    private final Players players;
    private final Dealer dealer;

    private BlackjackGame(Deck deck, Players players, Dealer dealer) {
        this.deck = deck;
        this.players = players;
        this.dealer = dealer;
    }

    public static BlackjackGame init() {
        Deck deck = Deck.create();
        Players players = requestPlayers(deck);
        Dealer dealer = new Dealer(deck);
        return new BlackjackGame(deck, players, dealer);
    }

    public void run() {
        printPlayersCard(toPlayersDto(), PlayerDto.from(dealer));

        takeTurnsPlayers();
        takeTurnDealer();

        printPlayersResult(toPlayersDto(), PlayerDto.from(dealer));
        printScoreResult(players.compete(dealer));
    }

    private static Players requestPlayers(Deck deck) {
        List<String> inputNames = InputView.requestNames();

        try {
            List<Player> players = inputNames.stream()
                .map(String::trim)
                .map(input -> new Player(input, deck))
                .collect(Collectors.toList());
            return new Players(players);
        } catch (IllegalArgumentException exception) {
            printException(exception);
            return requestPlayers(deck);
        }
    }

    private void takeTurnsPlayers() {
        for (Player player : players.getValue()) {
            takeTurn(player);
        }
    }

    private void takeTurn(Player player) {
        if (player.isBust()) {
            return;
        }
        if (!Selection.isYes(requestSelection(player))) {
            return;
        }
        player.drawCard(deck);
        printPlayerCards(PlayerDto.from(player));
        takeTurn(player);
    }

    private void takeTurnDealer() {
        while (dealer.isDrawable()) {
            printDealerDrawMessage();
            dealer.drawCard(deck);
        }
    }

    private Selection requestSelection(Player player) {
        try {
            return Selection.from(InputView.requestDrawCommand(PlayerDto.from(player)));
        } catch (IllegalArgumentException exception) {
            printException(exception);
            return requestSelection(player);
        }
    }

    private List<PlayerDto> toPlayersDto() {
        return players.getValue()
            .stream()
            .map(PlayerDto::from)
            .collect(Collectors.toList());
    }

}
