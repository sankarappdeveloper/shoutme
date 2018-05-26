package org.shoutme.status;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.special.ResideMenu.ResideMenu;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午1:33
 * Mail: specialcyci@gmail.com
 */
public class HomeFragment extends Fragment {
    FirebaseDatabase database;
    DatabaseReference mref;
    private View parentView;
    private ResideMenu resideMenu;
    Uri mInvitationUrl;
    Uri ss;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        Button butt=(Button)parentView.findViewById(R.id.send);
        TextView code=(TextView)parentView.findViewById(R.id.refercode);

        final EditText name=(EditText)parentView.findViewById(R.id.name);
        final EditText url=(EditText)parentView.findViewById(R.id.url);
        Button urladd=(Button)parentView.findViewById(R.id.add);
        urladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                mref = database.getReference("Videos").push();
                mref.child("name").setValue(name.getText().toString());
                mref.child("url").setValue(url.getText().toString());
            }
        });
        SharedPreferences mref = getActivity().getSharedPreferences("Login", 0);
        SharedPreferences.Editor editor = mref.edit();
        final String refer=mref.getString("refercode",null);
        code.setText(refer);

      butt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        String referrerName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Shoutmetamil");
        String sAux ="https://play.google.com/store/apps/details?id=Orion.Soft \n\n"+"Enter Referral code and get money"+refer;
        i.putExtra(Intent.EXTRA_TEXT, sAux);
        startActivity(Intent.createChooser(i, "choose one"));
    }
});
        return parentView;
    }

//    private void setUpViews() {
//        MainActivity parentActivity = (MainActivity) getActivity();
//        resideMenu = parentActivity.getResideMenu();
//
//        parentView.findViewById(R.id.btn_open_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//            }
//        });
//
//        // add gesture operation's ignored views
//        FrameLayout ignored_view = (FrameLayout) parentView.findViewById(R.id.ignored_view);
//        resideMenu.addIgnoredView(ignored_view);
//    }

}
