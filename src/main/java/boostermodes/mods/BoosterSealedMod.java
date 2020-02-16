package boostermodes.mods;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.neow.NeowEvent;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(
        clz = NeowEvent.class,
        method = "dailyBlessing"
)
public class BoosterSealedMod {
    private static final int RARES = 2;
    private static final int UNCOMMONS = 10;

    private static final int TOTAL = 30;
    private static final int RARE_START = TOTAL - RARES;
    private static final int UNCOMMON_START = RARE_START - UNCOMMONS;

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("rollRarity")) {
                    m.replace(String.format(
                            "{$_ = %s.getRarity(i);}",
                            BoosterSealedMod.class.getName()));
                }
            }
        };
    }

    public static AbstractCard.CardRarity getRarity(int i) {
        if (i >= RARE_START) {
            return AbstractCard.CardRarity.RARE;
        } else if (i >= UNCOMMON_START) {
            return AbstractCard.CardRarity.UNCOMMON;
        } else {
            return AbstractCard.CardRarity.COMMON;
        }
    }

}
