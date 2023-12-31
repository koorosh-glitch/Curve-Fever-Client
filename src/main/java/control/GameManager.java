package control;

import UI.FrameController;
import UI.lobby.LobbyController;
import UI.setup.SetupController;
import UI.welcome.WelcomeController;
import client.Client;
import common.game.logic.Colour;
import common.net.data.Command;
import common.persistence.Config;
import common.policies.RetrievalBehaviour;
import common.policies.Validator;
import common.util.CommandFactory;
import game.GameContainer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {
    private final static GameManager instance = new GameManager();
    private GameManager() {}

    public static GameManager getInstance() {return instance;}

    private final ExecutorService executors = Executors.newSingleThreadExecutor();

    private final Client client = new Client();

    private GameContainer gameContainer;

    private final FrameController welcomeController = new WelcomeController();

    private final FrameController setupController = new SetupController();

    private final LobbyController lobbyController = new LobbyController();

    public void start() {
        Config.getInstance().setPath("./src/main/resources/Config.properties");
        client.setRetrievalAction(new RetrievalBehaviour(client, new Validator()));
        client.init();
        try {
            client.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        showWelcome();
    }

    public void showWelcome() {
        welcomeController.show();
    }

    public void showSetup() {
        welcomeController.hide();
        setupController.show();
    }

    public void joinServer() {
        client.send(CommandFactory.join(client.getServer()));
    }

    public void setName(String name) {
        client.send(CommandFactory.setName(client.getServer(), name));
    }

    public void showLobby(String[] users) {
        setupController.hide();
        lobbyController.show();
        lobbyController.setUsers(users);
    }

    public void showOffer(String from) {
        lobbyController.showOffer(from);
    }

    public void invitePlayer(String playerName) {
        client.send(CommandFactory.invite(client.getServer(), playerName));
    }

    public void accept(String name) {
        client.send(CommandFactory.result(client.getServer(), "acc", name));
    }

    public void setUpFrame(String colourName) {
        var colour = Colour.getColor(colourName);
        if (colour.isEmpty())
            return;
        lobbyController.hide();
        gameContainer = new GameContainer(colour.get());
    }

    public void syncWorlds(Map<?, ?> headers) {
        gameContainer.sync(headers);
    }

    public void sendKeyEvent(int keyCode, boolean pressed) {
        client.send(CommandFactory.keyPressed(client.getServer(), keyCode, pressed));
    }

    public void terminateGame() {
        gameContainer.killGame();
        setupController.show();
    }

    public void execute(Command command) {
        executors.submit(command);
    }
}
