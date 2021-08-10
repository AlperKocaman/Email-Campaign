import React, {Component} from "react";
import { Button } from 'primereact/button';
import { auth, generateUserDocument } from "./Firebase";
import AdminPages from "./AdminPages";
import { navigate } from '@reach/router';

class Main extends Component {
    constructor(props) {
        super(props);
    }

    render() {
            return (

                <div  style = {{width: '98vw', height: '100vh'}}>
                  <span className="p-buttonset" >
                      <div style = {{marginBottom : '0.5%',  backgroundColor: '#2196F3', display: 'flex-end', flexDirection: 'row'}} >
                      <Button style = {{fontSize: '20px', textColor:'#2196F3'}} label="Problems" className="p-button-raised  p-text-bold" onClick = { () => { navigate('/admin/problems')} } />
                      <Button style = {{fontSize: '20px', textcolor:'#2196F3'}} label="Submissions" className="p-button-raised  p-text-bold" onClick = { () => { navigate('/admin/submissions') } } />
                      <Button style = {{fontSize: '20px', textcolor:'#2196F3'}} label="Users" className="p-button-raised  p-text-bold" onClick = { () => { navigate('/admin/users') } } />
                      <Button style = {{fontSize: '20px', textcolor:'#2196F3'}} label="Comments" className="p-button-raised  p-text-bold" onClick = { () => {navigate('/admin/comments') } } />
                      <Button style = {{fontSize: '20px', textcolor:'#2196F3'}} label="Templates" className="p-button-raised  p-text-bold" onClick = { () => { navigate('/admin/templates') } } />
                      <Button style = {{fontSize: '20px', textcolor:'red', float: "right"}} label="Sign out" className="p-button-raised  p-text-bold" onClick = { () => {auth.signOut(); navigate('/') } } />
                      </div>
                  </span>
                    <AdminPages/>
                </div>
            )
        }
}
export default Main;




