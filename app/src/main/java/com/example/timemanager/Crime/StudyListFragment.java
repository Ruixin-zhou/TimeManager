package com.example.timemanager.Crime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.ClassTable.pojo.Class_Table_Course;
import com.example.timemanager.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudyListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    public static final String EXTRA_STUDY_SUM =
            "com.example.timemanager.Crime.study_sum";

    private TextView mFreeTimeText;

    private RecyclerView mStudyRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    private static List<Class_Table_Course> allClass;
    private List<Class_Table_Course> useClass;

    private final Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH) + 1;
    private int day = calendar.get(Calendar.DATE);
    private int date = calendar.get(Calendar.DAY_OF_WEEK) - 1;    //当天的星期

    public static int study_flag = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        allClass = (List<Class_Table_Course>)getActivity()
                .getIntent().getSerializableExtra(AllEventFragment.EXTRA_ALLEVENT_ALLCLASS);
        if(study_flag == 0)
        {
            useClass = chooseClass(allClass);        //需要生成自习计划的课程
            addClassCrime(useClass);
            study_flag = 1;
        }



    }



    private List<Class_Table_Course> chooseClass(List<Class_Table_Course> allClass) {
        List<Class_Table_Course> fin = new ArrayList<>();
        Class_Table_Course course;
        int count = allClass.size();

        for(int i = 0;i<count;i++){
            course = allClass.get(i);
            if(!course.getCourseName().equals("体育（篮球）")){
                String[] courseArray = course.getCourseTime().split(";");
                for (int j = 0; j < courseArray.length; j++){
                    String[] info = courseArray[j].split(":");
                    int flag = Integer.parseInt(info[0]);
                    if(date + 1 == flag || date + 2 == flag){
                        fin.add(course);
                        break;
                    }
                    else if (date >= 5 && (flag == 1 || flag == 2)){
                        fin.add(course);
                    }
                }
            }

        }
        return fin;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study_list, container, false);

        mFreeTimeText = (TextView)view.findViewById(R.id.free_time_text);

        mStudyRecyclerView = (RecyclerView) view
                .findViewById(R.id.study_recycler_view);
        mStudyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        updateFreeTime();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        updateFreeTime();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);        //添加crime
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addClassCrime(List<Class_Table_Course> courses){
        for (int i = 0 ; i < courses.size();i ++){
            Class_Table_Course course = new Class_Table_Course();
            Crime crime = new Crime();

            course = courses.get(i);

            crime.setTitle(course.getCourseName());
            crime.setUseMinute(""+course.getUseMinute());
            ClassToTime(course,crime);

            CrimeLab.get(getActivity()).addCrime(crime);
        }

    }

    public void ClassToTime(Class_Table_Course course,Crime crime){
        int section =0;

        String CourseTime = course.getCourseTime();
        String[] courseArray = CourseTime.split(";");
        for (int i = 0; i < courseArray.length; i++) {
            String[] info = courseArray[i].split(":");
            if(date == 5 ||date == 6 ||date == 7){    //当天是周五或周末的话 选择周一与周二的课
                switch (date){
                    case 5:
                        if(Integer.parseInt(info[0]) == 1 ){
                            crime.setDate(new Date(year - 1900,month - 1,day + 3));
                        }else {
                            crime.setDate(new Date(year - 1900,month - 1,day + 4));
                        }
                        break;
                    case 6:
                        if(Integer.parseInt(info[0]) == 1 ){
                            crime.setDate(new Date(year - 1900,month - 1,day + 2));
                        }else {
                            crime.setDate(new Date(year - 1900,month - 1,day + 3));
                        }
                        break;

                        default:
                            if(Integer.parseInt(info[0]) == 1 ){
                                crime.setDate(new Date(year - 1900,month - 1,day + 1));
                            }else {
                                crime.setDate(new Date(year - 1900,month - 1,day + 2));
                            }

                }
                if(Integer.parseInt(info[0]) == 1 || Integer.parseInt(info[0]) == 2){
                    section = Integer.parseInt(info[1]);
                    break;
                }
            }
            else {
                if (Integer.parseInt(info[0]) == date+1 || Integer.parseInt(info[0]) == date+2){
                    section = Integer.parseInt(info[1]);
                    break;
                }
            }
        }
        String time = "";
        switch (section){
            case 1:
                time = "08:30";
                break;
            case 2:
                time = "09:10";
                break;
            case 3:
                time = "10:05";
                break;
            case 4:
                time = "11:15";
                break;
            case 5:
                time = "14:00";
                break;
            case 6:
                time = "14:40";
                break;
            case 7:
                time = "15:35";
                break;
            default:
                time = "19:00";

        }
        String[] info = time.split(":");
        crime.setHour(Integer.parseInt(info[0]));
        crime.setMinute(Integer.parseInt(info[1]));
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }



    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mStudyRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private void updateFreeTime() {
        String sections="";
        for (int i = 0; i < allClass.size(); i++) {
            Class_Table_Course course = allClass.get(i);
            String CourseTime = course.getCourseTime();
            String[] courseArray = CourseTime.split(";");
            for (int j = 0; j < courseArray.length; j++) {
                String[] info = courseArray[j].split(":");
                if (Integer.parseInt(info[0]) == date) {    //选中某一节课
                    sections = sections + info[1];          //存放当日所有课次

                }
            }
        }
        mFreeTimeText.setText(SectionsToTime(findFreeSections(sections)));

    }

    private String findFreeSections(String busySections){     //输入上课的节次 输出空闲的时间
        String freeTime = "";
        int i,j,k=0;
        String a="13579";
        for(i=0;i<a.length();i++)
        { for(j=0;j<busySections.length();j++)
        { if(a.charAt(i)==busySections.charAt(j))
        { a=a.replace(a.charAt(i),'0');
            busySections=busySections.replace(busySections.charAt(j),'0');
        }
        }
        }//找出两个字符串中相同的字符，改为0
        for(i=0;i<a.length();i++)
        { if(a.charAt(i)!='0')
        {
            freeTime+="0";
            freeTime=freeTime.replace(freeTime.charAt(k),a.charAt(i));
            k++;
        }
        }
        for(j=0;j<busySections.length();j++)
        { if(busySections.charAt(j)!='0')
        {
            freeTime+="0";
            freeTime=freeTime.replace(freeTime.charAt(k),busySections.charAt(j));
            k++;
        }
        }
        return freeTime;
    } //找出空闲的课次
    private String SectionsToTime(String sections){
        String freetime = "";
        String[] AllTime = {"8:30～10:05","10:25～12:00","14:00～15:35","15:55～17:30","19:00～20:45"};
        int[] flag = new int[sections.length()];
        char[] ch = sections.toCharArray();
        for(int i = 0 ;i < sections.length();i++){
            flag[i] = (int) ch[i] - 48;
            flag[i] = flag [i]/2;
        }
        if(flag.length == 5){
            freetime = "今日全天空闲";
        }else {
            for (int i = 0; i < flag.length ;i++){
                freetime = freetime+AllTime[flag[i]]+"\n";
            }
        }

        return freetime;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder 
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            String date = mCrime.getMonth()+1+"月"+mCrime.getdate()+"日 星期"+mCrime.getday()+"  "+mCrime.gethour()+":"+mCrime.getminute();
            String cost = "\n预计花费" + mCrime.getmUseMinute() +"分钟";
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(date+cost);
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
            sendStudyMessage(mCrimes.size());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    public void sendStudyMessage(int dailysum){
        Intent data = new Intent();
        data.putExtra(EXTRA_STUDY_SUM, (Serializable) dailysum);
        getActivity().setResult(Activity.RESULT_OK,data);
    }
}
