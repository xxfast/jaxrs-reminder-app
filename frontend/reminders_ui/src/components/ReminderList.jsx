import { Grid } from '@mui/material';
import Reminder from "./Reminder";

export default function ReminderList(props) {
    return (
        <Grid container spacing={{ xs: 1, md: 2 }} columns={{ xs: 2, sm: 2, md: 3, lg: 4 }}>
            {props.data.reminders.map((reminder) => (
                <Grid item xs={1} sm={1} md={1} key={reminder.id} >
                    <Reminder id={reminder.id} content={reminder.content}/>
                </Grid>
              ))}
        </Grid>
    )
}