import { useLocation } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import DashSidebar from './DashSidebar';
import DashProfile from '../components/Dashboard/DashProfile';
import DashAdmin from '../components/Dashboard/DashAdmin';
import DashUsers from '../components/Dashboard/DashUsers';
import DashFeedbacks from '../components/Dashboard/DashFeedbacks';
import DashProblems from '../components/Dashboard/DashProblems';

const Dashboard = () => {
    const location = useLocation();
    const [tab, setTab] = useState('');

    useEffect(() => {
        const urlParams = new URLSearchParams(location.search);
        const tabFromUrl = urlParams.get('tab');
        if (tabFromUrl) {
            setTab(tabFromUrl);
        }
    }, [location.search]);

    return (
        <div className='dashboard-container' style={{display: 'flex', flexDirection: 'row'}}>
            <div className={`dashboard-sidebar ${tab ? 'dashboard-sidebar-md-inline' : ''}`}>
                <DashSidebar />
            </div>
            {tab === 'dashboard' && <div className='dashboard-content tab-dashboard'>
                <DashAdmin />
            </div>}
            {tab === 'profile' && <div className='dashboard-content tab-profile'>
                <DashProfile />
            </div>}
            {tab === 'feedbacks' && <div className='dashboard-content tab-feedbacks'>
                <DashFeedbacks />
            </div>}
            {tab === 'problems' && <div className='dashboard-content tab-problems'>
                <DashProblems />
            </div>}
            {tab === 'users' && <div className='dashboard-content tab-users'>
                <DashUsers />
            </div>}
        </div>
    );
};

export default Dashboard;