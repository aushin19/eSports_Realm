package com.cornerdesk.esportrealm.ViewHolder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cornerdesk.esportrealm.Fragments.Login_Fragment;
import com.cornerdesk.esportrealm.Fragments.Signup_Fragment;

public class RegisterFragmentAdapter extends FragmentStateAdapter {
    public RegisterFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new Signup_Fragment();
        }

        return new Login_Fragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
