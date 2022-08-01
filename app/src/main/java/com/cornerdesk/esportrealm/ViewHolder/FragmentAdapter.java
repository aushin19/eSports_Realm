package com.cornerdesk.esportrealm.ViewHolder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cornerdesk.esportrealm.Fragments.MyDashboard;
import com.cornerdesk.esportrealm.Fragments.MyOngoing;
import com.cornerdesk.esportrealm.Fragments.MyResults;
import com.cornerdesk.esportrealm.Fragments.MyWallet;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new MyOngoing();
            case 2 :
                return new MyResults();
            case 3 :
                return new MyWallet();
        }

        return new MyDashboard();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
