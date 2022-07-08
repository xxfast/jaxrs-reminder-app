import { useState, useContext }  from 'react';
import Cookies from 'js-cookie';
import {
    Card, 
    CardActions, 
    CardContent, 
    IconButton, 
    TextField, 
    Typography
} from '@mui/material';
import CancelIcon from '@mui/icons-material/Cancel';
import CheckIcon from '@mui/icons-material/Check';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';

import {ResourceContext} from './generic/resourceContainer'

export default function Reminder(props) {
    const new_reminder = Boolean(props.new_reminder)
    const original_content = props.content || ''

    const [editedContent, setEditedContent] = useState(original_content);
    const [editMode, setEditMode] = useState(new_reminder);
    const resourceContext = useContext(ResourceContext); 

    function handleEditCancel() {
        setEditMode(new_reminder)
        setEditedContent(original_content)
    };

    const handleEditChange = (event) => {
        setEditedContent(event.target.value);
    };

    function confirmChanges(id, content) {
        setEditMode(new_reminder);

        const method = ( new_reminder ? 'POST' : 'PUT' )
        const url = ( new_reminder ? "/api/reminder" : "/api/reminder/" + id ) 

        // Send request
        const requestOptions = {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': Cookies.get('csrf_access_token')
            },
            body: JSON.stringify({content})
        };

        fetch(url, requestOptions)
            .then(res => res.json())
            .then(
                (data) => {
                    resourceContext.setResourceDirty(current => !current)
                },
                (error) => {
                    alert("error - " + JSON.stringify(error));
                }
            )
    }

    function deleteReminder(id) {
        const requestOptions = {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': Cookies.get('csrf_access_token')
            },
        };

        const url = "/api/reminder/" + id
        console.log("delete(" + url + ")")
        fetch(url, requestOptions)
            .then(res => res.json())
            .then(
                (data) => {
                    resourceContext.setResourceDirty(current => !current)
                },
                (error) => {
                    alert("error - " + JSON.stringify(error));
                }
            )
    }

    return (
        <Card sx={{ minWidth: 200, maxWidth: 300 }}>
            <CardContent style={{backgroundColor: "LightYellow"}}>
                { editMode ?
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Reminder text"
                        defaultValue={original_content}
                        fullWidth
                        variant="standard"
                        multiline
                        maxRows={4}
                        onChange={handleEditChange}
                    /> :
                    <Typography style={{color: "Black"}} variant="subtitle1">
                        {props.content}
                    </Typography>
                }
            </CardContent>
            <CardActions style={{backgroundColor: "LightYellow"}}>
                { editMode ?
                    <>
                        <IconButton aria-label="save" size="small" onClick={() => confirmChanges(props.id, editedContent)}>
                            <CheckIcon/>
                        </IconButton>
                        <IconButton aria-label="cancel edit" size="small" onClick={handleEditCancel}>
                            <CancelIcon/>
                        </IconButton>
                    </> :
                    <>
                        <IconButton aria-label="edit" size="small" onClick={() => setEditMode(true)}>
                            <EditIcon/>
                        </IconButton>
                        <IconButton aria-label="delete" size="small" onClick={() => deleteReminder(props.id)}>
                            <DeleteIcon/>
                        </IconButton>
                    </>
                }
            </CardActions>
        </Card>        
    )
}