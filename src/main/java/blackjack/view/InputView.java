package blackjack.view;

import blackjack.domain.Name;
import blackjack.domain.player.Gambler;
import java.util.Scanner;

public class InputView {

    private static Scanner scanner = new Scanner(System.in);

    public static String inputNames() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
        return scanner.nextLine();
    }

    public static String inputMoreCard(Gambler gambler) {
        System.out.printf("%s는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)\n", gambler.getName());
        return scanner.nextLine();
    }

    public static String inputMoney(Name playerName) {
        System.out.printf("%s의 배팅 금액은?", playerName);
        System.out.println();
        return scanner.nextLine();
    }
}