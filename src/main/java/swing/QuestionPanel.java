package swing;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class QuestionPanel extends JPanel {
    private final JLabel label;
    private final JButton button;
    private final JTextArea textArea;
    private BaseQuestionDlg baseQuestionDlg;

    QuestionPanel(JLabel label, JButton button, JTextArea textArea) {
        this.label = label;
        this.button = button;
        this.textArea = textArea;

        setLayout(new MigLayout(
                "fill , ins 0",
                "[left][grow,right]",
                ""
        ));


        add(label, "align left");

        button.setIcon(IconFontSwing.buildIcon(FontAwesome.TRASH, 16, Color.RED));
        button.addActionListener(e -> {
            this.setVisible(FALSE);

//            JButton b = (JButton) e.getSource();
//            Container container = b.getParent().getParent();

            baseQuestionDlg = (BaseQuestionDlg) SwingUtilities.getWindowAncestor(this);
            JPopupMenu popupMenu = baseQuestionDlg.getTargetGroupPopUp();

            JMenuItem item = new JMenuItem(this.label.getText());

            popupMenu.add(item);

            item.addActionListener(click->{
                baseQuestionDlg.addQuestionText((JMenuItem) click.getSource());

            });

            int popUpCount = popupMenu.getComponentCount();
            if ( popUpCount > 0 && !baseQuestionDlg.getTargetGroupButton().isEnabled()) {
                baseQuestionDlg.getTargetGroupButton().setEnabled(TRUE);
            }

            if(popUpCount == BaseQuestionDlg.targetGroups.values().length){
                baseQuestionDlg.hideDefaultLabel();
                baseQuestionDlg.hideQuestionsPanelBorder();
            }

            this.button.getParent().getParent().remove(this);
//            container.remove(this);
        });

        add(button, "align right");

        textArea.setBackground(Color.white);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(5);

        add(textArea, "newline, span 2, growx, wmin 1");
    }


}
