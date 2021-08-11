import React, {Component} from "react";
import { Button } from 'primereact/button';
import RoutePages from "./RoutePages";
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
                      <Button style = {{fontSize: '20px', textColor:'#2196F3'}} label="Contacts" className="p-button-raised  p-text-bold" onClick = { () => { navigate('/contacts')} } />
                      <Button style = {{fontSize: '20px', textcolor:'#2196F3'}} label="Groups" className="p-button-raised  p-text-bold" onClick = { () => { navigate('/groups') } } />
                      <Button style = {{fontSize: '20px', textcolor:'#2196F3'}} label="Campaigns" className="p-button-raised  p-text-bold" onClick = { () => { navigate('/campaigns') } } />
                      <Button style = {{fontSize: '20px', textcolor:'#2196F3'}} label="Send Mail" className="p-button-raised  p-text-bold" onClick = { () => {navigate('/sendmail') } } />
                      </div>
                  </span>
                    <RoutePages/>
                </div>
            )
        }
}
export default Main;




