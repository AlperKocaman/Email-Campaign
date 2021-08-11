import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import React, {Component} from 'react';
import classNames from 'classnames';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import ContactService from '../service/ContactService';
import { Toast } from 'primereact/toast';
import { Button } from 'primereact/button';
import { FileUpload } from 'primereact/fileupload';
import { Toolbar } from 'primereact/toolbar';
import { Dialog } from 'primereact/dialog';
import { InputText } from 'primereact/inputtext';
import { SelectButton } from 'primereact/selectbutton';
import uuid from 'uuid-random';
import {Dropdown} from "primereact/dropdown";
import {MultiSelect} from "primereact/multiselect";


export class Contact extends Component {

    emptyContact = {
        id: null,
        name: null,
        surname:null,
        emailAddress: '',
        isMailSentToThisContact: false,
        isThisContactClickedTheLink: false,
        elapsedTimeUntilClick: 0
    };

    constructor(props) {
        super(props);
        this.state = {
            contacts: [],
            contact: this.emptyContact,
            globalFilter: null
        }

        this.contactService = new ContactService();
        this.leftToolbarTemplate = this.leftToolbarTemplate.bind(this);
        this.sendMailBodyTemplate = this.sendMailBodyTemplate.bind(this);
        this.clickBodyTemplate = this.clickBodyTemplate.bind(this);
        this.exportCSV = this.exportCSV.bind(this);
        this.onInputChange = this.onInputChange.bind(this);
        this.uploadFile = this.uploadFile.bind(this);
    }

    componentDidMount = async () => {
        this.contactService.getContacts().then(res => {
            this.setState({contacts: res.data});
        });
    }

    findIndexById(id) {
        let index = -1;
        for (let i = 0; i < this.state.users.length; i++) {
            if (this.state.users[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    }

    createId() {
        return uuid();
    }

    exportCSV() {
        this.dt.exportCSV();
    }


    onInputChange(e, name) {
        let val = (e.target && e.target.value) || '';
        if(val === 'false'){
            val = false;
            this.setState({isAdmin: 'false'} );
        }else if(val === 'true'){
            val = true;
            this.setState({isAdmin: 'true'} );
        }
        let user = {...this.state.user};
        user[`${name}`] = val;
        if (val === '' || val === null || val === undefined || val === NaN) {
            this.setState({ hasInvalidInput: true })
        }else {
            this.setState({hasInvalidInput: false});
        }
        this.setState({ user });
    }

    leftToolbarTemplate() {
        return (
            <div className="p-d-flex p-ai-center p-py-2">
                <FileUpload chooseOptions={{ label: 'Import', icon: 'pi pi-file-o' }}
                mode="basic" name="file" auto url="http://localhost:8080/api/upload"
                accept=".txt" className="p-mr-2" onUpload={this.uploadFile} />
            </div>
        )
    }

    uploadFile = (e) => {
      console.log("File successfully uploaded!");
    };

    clickBodyTemplate(rowData) {
        return <span className={`contact-badge status-${rowData.thisContactClickedTheLink ? 'true' : 'false'}`}>{rowData.thisContactClickedTheLink ? 'YES' : 'NO'}</span>;
    }

    sendMailBodyTemplate(rowData) {
        return <span className={`contact-badge status-${rowData.mailSentToThisContact ? 'true' : 'false'}`}>{rowData.mailSentToThisContact ? 'YES' : 'NO'}</span>;
    }

    render() {
        const header = (
            <div className="table-header">
                <h5 className="p-m-0">Contacts</h5>
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText type="search" onInput={(e) => this.setState({ globalFilter: e.target.value })} placeholder="Search..." />
                </span>
            </div>
        );

        return (
            <div className="datatable-crud-demo">
                <Toast ref={(el) => this.toast = el} />

                <div className="card">
                    <Toolbar className="p-mb-4" left={this.leftToolbarTemplate}></Toolbar>

                    <DataTable ref={(el) => this.dt = el} value={this.state.contacts}
                               dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]}
                               paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                               currentPageReportTemplate="Showing {first} to {last} of {totalRecords} contacts"
                               globalFilter={this.state.globalFilter}
                               header={header}>

                        <Column selectionMode="multiple" headerStyle={{ width: '3rem' }}></Column>
                        <Column field="name" body={this.linkable} header="Name" sortable></Column>
                        <Column field="surname" header="Surname" sortable></Column>
                        <Column field="emailAddress" header="E-mail Address" sortable></Column>
                        <Column field="isMailSentToThisContact" header="Mail Sending Status" body={this.sendMailBodyTemplate} sortable></Column>
                        <Column field="isThisContactClickedTheLink" header="Link Click Status" body={this.clickBodyTemplate} sortable></Column>
                        <Column field="elapsedTimeUntilClick" header="Elapsed Time To Click" sortable></Column>
                    </DataTable>
                </div>
            </div>
        );
    }
}

export default Contact;