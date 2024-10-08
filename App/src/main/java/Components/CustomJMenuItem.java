package Components;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class CustomJMenuItem extends JMenuItem {


    private final Color disabledColor = new Color(136, 136, 136, 15);
    private final Color foreColor = new Color(0, 0, 0);

    public CustomJMenuItem(JFrame root, JPanel father, String text, Container child) {
        setText(text);
        setPreferredSize(new Dimension(100, 30));
        setBackground(Color.BLACK);
        setForeground(foreColor);
        addActionListener(e -> {
            if (!root.getName().equals(child.getName())) {
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.CENTER;
                for (Component c : father.getComponents()
                ) {
                    father.remove(c);
                }
                father.setBorder(new BevelBorder(BevelBorder.RAISED));
                father.add(child, gbc);
                father.revalidate();
                father.repaint();
            }
            root.setName(child.getName());
            root.setTitle(child.getName());
            root.revalidate();
            root.repaint();
        });
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setBackground(Color.BLACK);
        } else {
            setBackground(disabledColor);
            setForeground(foreColor);
        }
    }
}
