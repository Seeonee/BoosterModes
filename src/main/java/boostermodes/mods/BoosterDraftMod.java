package boostermodes.mods;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(
        clz = CardRewardScreen.class,
        method = "draftOpen"
)
public class BoosterDraftMod {
    private static final int RARES = 1;
    private static final int UNCOMMONS = 4;

//    private static final int TOTAL = 15;
    private static final int RARE_END = RARES;
    private static final int UNCOMMON_END = RARE_END + UNCOMMONS;

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("returnRandomCard")) {
                    m.replace(String.format(
                            "{$_ = %s.getCard(this.draftCount);}",
                            BoosterDraftMod.class.getName()));
                }
            }
        };
    }

    public static AbstractCard getCard(int i) {
        if (i <= RARE_END) {
            return AbstractDungeon.getCard(AbstractCard.CardRarity.RARE);
        } else if (i <= UNCOMMON_END) {
            return AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON);
        } else {
            return AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON);
        }
    }

}
