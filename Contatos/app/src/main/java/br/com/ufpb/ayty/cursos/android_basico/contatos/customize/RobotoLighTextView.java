package br.com.ufpb.ayty.cursos.android_basico.contatos.customize;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Raphael on 03/05/2015.
 * SDK ${VERSION_SDK}
 */
public class RobotoLighTextView extends TextView {

    public RobotoLighTextView(Context context) {
        super(context);
    }

    public RobotoLighTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RobotoLighTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void alterarFonte(Context context){
        if(!isInEditMode()){ //Se nao estiver em modo de edicao
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_bold.ttf");
            setTypeface(typeface);
        }
    }
}
