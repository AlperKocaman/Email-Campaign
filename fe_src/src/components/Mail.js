import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import React, {Component} from 'react';
import MailService from "../service/MailService";
import ContactService from "../service/ContactService";
import { Editor } from 'primereact/editor';
import {InputText} from "primereact/inputtext";
import {MultiSelect} from "primereact/multiselect";
import {Button} from "primereact/button";


export class Contact extends Component {

    email = {
        contactEmailAddressList: [],
        topic: null,
        body: null
    };

    constructor(props) {
        super(props);
        this.state = {
            contactEmailAddressList: [],
            allContactsEmailList: [],
            topic: null,
            body: null,
            globalFilter: null
        }

        this.mailService = new MailService();
        this.contactService = new ContactService();
        this.extractAndSendMail = this.extractAndSendMail.bind(this);
    }

    componentDidMount = async () => {
        this.contactService.getEmailList().then(res => {
            this.setState({allContactsEmailList: res.data});
        });
    }

    extractAndSendMail() {
        this.email.contactEmailAddressList = this.state.selectedContactsEmailAddressList;
        this.email.topic = this.state.topic;
        this.email.body = this.state.body;
        this.mailService.sendMail(this.email).then(res => {
            console.log("Emails send successfully!");
        });
    }

    render() {
        return (
            <div>
                <div className="p-col-12 p-md-4">
                    <MultiSelect value={this.state.selectedContactsEmailAddressList} options={this.state.allContactsEmailList} onChange={(e) => this.setState({ selectedContactsEmailAddressList: e.value })}  placeholder="Select Recipients" display="chip" />
                </div>
                <div className="p-col-12 p-md-4">
                    <div className="p-inputgroup">
                                <span className="p-inputgroup-addon">
                                    <i className="pi pi-book"></i>
                                </span>
                        <InputText placeholder="Insert your topic here!" value={this.state.topic} onChange={(e) => this.setState({ topic: e.target.value})}/>
                    </div>
                </div>
                <div className="p-col-12 p-md-4">
                    <Editor style={{height: '500px' }} value={this.state.body} onTextChange={(e) => this.setState({ body: e.htmlValue })} />
                </div>
                <div className="p-col-12 p-md-4">
                    <Button label="Send" icon="pi pi-send" className="p-button-success p-mr-2" onClick={this.extractAndSendMail}/>
                </div>
            </div>
        );
    }
}

export default Contact;