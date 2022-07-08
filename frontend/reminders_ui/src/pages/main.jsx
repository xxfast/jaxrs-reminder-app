import { Divider, Stack } from '@mui/material';

import '../components/Layout.css';
import Reminder from "../components/Reminder";
import ReminderList from "../components/ReminderList";
import ResourceContainer from '../components/generic/resourceContainer';

export default function Main() {
    return (
        <Stack direction="column" alignItems="center" spacing={1} divider={<Divider flexItem />}>
            <ResourceContainer url={"/api/reminder"}>
                <Reminder new_reminder/>
                <ReminderList className="App-main"/>
            </ResourceContainer>
        </Stack>
    )
}