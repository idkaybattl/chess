package ui.screens.components;

import javax.swing.*;

import logic.Location;
import logic.pieces.Pawn;
import ui.Controller;

public class BoardPanel extends JLayeredPane {
    private Controller controller;

    private BoardLayer boardLayer;
    private PromotionLayer promotionLayer;

    public BoardPanel(Controller controller) {
        super();

        this.controller = controller;

        boardLayer = new BoardLayer(controller);

        add(boardLayer);

        setPreferredSize(boardLayer.getPreferredSize());
    }

    @Override
    public void doLayout() {
        boardLayer.setBounds(0, 0, getWidth(), getHeight());
        if (promotionLayer != null) {
            promotionLayer.setBounds(0, 0, getWidth(), getHeight());
        }
    }

    public void updateBoard() {
        boardLayer.updateBoard();
    }

    public void startPromotion(Pawn pawn, Location origin, Location target) {
        if (promotionLayer != null) {
            remove(promotionLayer);
        }

        promotionLayer = new PromotionLayer(controller, origin, target, pawn, boardLayer.getIcons());
        add(promotionLayer, JLayeredPane.PALETTE_LAYER);

        promotionLayer.setBounds(0, 0, getWidth(), getHeight());
        moveToFront(promotionLayer);
        revalidate();
        repaint();
    }

    public void closePromotion() {
        if (promotionLayer != null) {
            remove(promotionLayer);
            promotionLayer = null;
            updateBoard();
            revalidate();
            repaint();
        }
    }
}
