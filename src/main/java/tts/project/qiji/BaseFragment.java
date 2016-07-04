package tts.project.qiji;

import android.content.Intent;
import android.util.Log;

import tts.moudle.api.TTSBaseFragment;


/**
 * Created by lenove on 2016/4/29.
 */
public class BaseFragment extends TTSBaseFragment {
    public final int getData = 1;
    public final int loadMore = 4;
    public final int cancel = 5;
    public final int modify = 6;
    public final int submit = 7;
    public final int confirm = 8;
    public final int hurryOrder = 9;



    @Override
    protected void doPendingFailed(int index, String error) {
        super.doPendingFailed(index, error);
        if (error.equals("token failed")) {
            Log.i("", "<<<<<<<<<<<<<<<<22222222222222");

//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivityForResult(intent, 3001);
        }
    }

    public void loginOk() {
        BaseApplication.getInstance().initMyUser(getActivity().getApplication());
    }

    public void loginFailed() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
//       intent.putExtra("isOut","1");
        intent.putExtra("init", "1");
        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3001:

                if (resultCode == getActivity().RESULT_OK) {
                    loginOk();

                } else {
                    loginFailed();
                }
                break;

        }
    }
}
