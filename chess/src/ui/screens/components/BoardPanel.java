package ui.screens.components;

import javax.swing.*;
import ui.Controller;

public class BoardPanel extends JLayeredPane {

    private BoardLayer boardLayer;
    private PromotionLayer promotionLayer;

    public BoardPanel(Controller controller) {
        super();

        boardLayer = new BoardLayer(controller);
        promotionLayer = new PromotionLayer(controller);

        add(boardLayer);
        add(promotionLayer);

        setPreferredSize(boardLayer.getPreferredSize());

        promotionLayer.setVisible(false);
    }

    @Override
    public void doLayout() {
        boardLayer.setBounds(0, 0, getWidth(), getHeight());
        promotionLayer.setBounds(0, 0, getWidth(), getHeight());
    }

    public void updateBoard() {
        boardLayer.updateBoard();
    }
}
