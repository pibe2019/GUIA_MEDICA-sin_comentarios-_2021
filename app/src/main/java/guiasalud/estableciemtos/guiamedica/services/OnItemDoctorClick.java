package guiasalud.estableciemtos.guiamedica.services;

import android.view.View;
import android.widget.RelativeLayout;

public interface OnItemDoctorClick {
    void onItemClick(View view, int position, RelativeLayout relativeLayout, RelativeLayout click);
}
