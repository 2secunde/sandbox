package swing;

import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class BaseQuestionDlg extends JFrame {

    private final JPanel mainPanel = new JPanel(new MigLayout("flowy", "[grow]", ""));

    private final JLabel questionTextLabel = new JLabel("Question Text");
    private final JLabel defaultTextLabel = new JLabel("Default");
    private final JPanel topPanel = new JPanel();
    private final JButton checkSpelling = new JButton();
    private final JButton targetGroupButton = new JButton();
    private final JPopupMenu targetGroupPopUp = new JPopupMenu();

    private final JPanel questionPanel = new JPanel(new MigLayout("flowy", "[grow]", ""));
    private final JTextArea questionText = new JTextArea();
    public static final Dimension MIN_SIZE = new Dimension(660, 500);

    public enum targetGroups {
        APPLICANT,
        BROKER,
        PHYSICIAN,
        TELEINTERVIEW
    }

    BaseQuestionDlg() {

        // Init icon font
        IconFontSwing.register(FontAwesome.getIconFont());

        setTitle("Base Question Properties");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(MIN_SIZE);

        //        setLayout(new BorderLayout());
//        JScrollPane scroll = new JScrollPane();
        JScrollPane scroll = new JScrollPane(mainPanel);

        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

//        add(mainPanel);
//        setLayout(new MigLayout("","[grow]",""));
        add(scroll);

        mainPanel.add(topPanel, "growx");
        mainPanel.add(questionPanel, "grow");

        JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.white);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setRows(5);

        if(questionPanel.getComponentCount() < 3){
            hideDefaultLabel();
            hideQuestionsPanelBorder();
        }
        questionPanel.add(defaultTextLabel);
        questionPanel.add(textArea, "growx, wmin 1");

        topPanel.setLayout(new MigLayout("fill , ins 0", "[left]5[grow, right]5[right]", ""));
        topPanel.add(questionTextLabel, "cell 0 0");

        checkSpelling.setText("Check Spelling");
        checkSpelling.setToolTipText("Check Spelling");
        checkSpelling.setIcon(IconFontSwing.buildIcon(FontAwesome.CHECK, 16, Color.BLACK));
        checkSpelling.addActionListener(e->{
            System.out.println("checked!");
        });

        topPanel.add(checkSpelling, "cell 1 0");

        targetGroupButton.setText("Target Group");
        targetGroupButton.setToolTipText("Target Group");
        targetGroupButton.setIcon(IconFontSwing.buildIcon(FontAwesome.PLUS, 16, Color.BLACK));

        targetGroupButton.addActionListener(
                e -> {
                    Component button = (Component) e.getSource();
                    targetGroupPopUp.show(
                            button,
                            button.getWidth() - targetGroupPopUp.getPreferredSize().width,
                            button.getHeight());
                });

        topPanel.add(targetGroupButton, "cell 2 0");

        Arrays.stream(targetGroups.values()).forEach(
                targetGroup -> {
                    JMenuItem jMenuItem = new JMenuItem(targetGroup.toString());
                    jMenuItem.addActionListener(e -> addQuestionText((JMenuItem) e.getSource()));
                    targetGroupPopUp.add(jMenuItem);
                }

        );
        pack();

    }

    public JButton getTargetGroupButton() {
        return targetGroupButton;
    }

    public JPopupMenu getTargetGroupPopUp() {
        return targetGroupPopUp;
    }

    public void hideDefaultLabel(){
        defaultTextLabel.setVisible(FALSE);
//        defaultTextLabel.setMaximumSize(new Dimension(defaultTextLabel.getWidth(), 1));
    }

    public void showDefaultLabel(){
//        defaultTextLabel.setMaximumSize(new Dimension(defaultTextLabel.getWidth(), questionTextLabel.getHeight()));
//        defaultTextLabel.setMinimumSize(new Dimension(defaultTextLabel.getWidth(), questionTextLabel.getHeight()));
        defaultTextLabel.setVisible(TRUE);

    }

    public void showQuestionsPanelBorder(){
        questionPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    public void hideQuestionsPanelBorder(){
        questionPanel.setBorder(null);
    }

    public void addQuestionText(JMenuItem item) {

        String label = item.getText();

        questionPanel.add(
                new QuestionPanel(
                        new JLabel(label),
                        new JButton(),
                        new JTextArea()),
                "growx");

        targetGroupPopUp.remove(item);

        if(!defaultTextLabel.isVisible()){
            showDefaultLabel();
            showQuestionsPanelBorder();
        }

        if (targetGroupPopUp.getComponentCount() == 0) {
            targetGroupButton.setEnabled(FALSE);
        }

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BaseQuestionDlg().setVisible(true);
            }
        });
    }
}
