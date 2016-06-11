package com.threemenstudio.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threemenstudio.data.Ability;
import com.threemenstudio.data.Path;
import com.threemenstudio.interfaces.PagerRadioButtonInterface;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.FragmentPathInfoBinding;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Shitman on 11/6/2016.
 */
public class PathInfoFragment  extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static Context context;
    private static Path path;
    private static List<Ability> abilities;
    private  LinkedHashMap<Integer, Boolean> opened = new LinkedHashMap<>();

    public PathInfoFragment() {
    }

    public static PathInfoFragment newInstance(int sectionNumber, Context context,
                                               Path path, List<Ability> abilities) {
        PathInfoFragment fragment = new PathInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        PathInfoFragment.path = path;
        PathInfoFragment.context = context;
        PathInfoFragment.abilities = abilities;
        Log.w("zzzz", abilities.get(sectionNumber).getSystem()+"");
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentPathInfoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_path_info, container, false);
        final int message = getArguments().getInt(ARG_SECTION_NUMBER);
        ((PagerRadioButtonInterface)context).setRadio(message, binding.getRoot());

        setStaticInfo(binding, message);

        setDynamicInfo(binding, message);
        return binding.getRoot();
    }

    private void setStaticInfo(final FragmentPathInfoBinding binding, final int message){

        binding.name.setText(path.getName());
        binding.desc.setText(path.getDescription());
        if(path.getAttribute() == null){
            binding.attribute.setVisibility(View.GONE);
        }
        else{
            binding.attributeDesc.setText(path.getAttribute());
        }

        if(path.getSystem() == null ){
            binding.system.setVisibility(View.GONE);
        }
        else{
            binding.systemDesc.setText(path.getSystem());
        }

        if(path.getPrice() == null){
            binding.price.setVisibility(View.GONE);
        }
        else{
            binding.priceDesc.setText(path.getPrice());
        }

        if(path.getOfficial() == null){
            binding.official.setVisibility(View.GONE);
        }
        else{
            binding.officialDesc.setText(path.getOfficial());
        }

        binding.headerDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opened.get(message)){
                    binding.description.setVisibility(View.GONE);
                    binding.arrow.setImageResource(android.R.drawable.arrow_down_float);
                    opened.put(message, false);
                }
                else{
                    binding.description.setVisibility(View.VISIBLE);
                    binding.arrow.setImageResource(android.R.drawable.arrow_up_float);
                    opened.put(message, true);
                }
            }
        });
        if(!opened.containsKey(message)){
            opened.put(message,false);
        }
    }

    private void setDynamicInfo(FragmentPathInfoBinding binding, int message) {

        binding.abilityName.setText(abilities.get(message).getTitle());
        binding.abilityDesc.setText(abilities.get(message).getDescription());
        binding.abilitySystemDesc.setText(abilities.get(message).getSystem());

        if(abilities.get(message).getSystem() == null){

            binding.abilitySystem.setVisibility(View.GONE);
            binding.abilitySystemDesc.setVisibility(View.GONE);

        }
    }
}
