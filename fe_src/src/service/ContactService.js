import axios from 'axios';

export default class ContactService {

    async requestToServer(url = '',method='', data = {}) {
        try {
            if (method==='POST'){
                return await axios.post(url, data);
            }else if (method==='GET'){
                return await axios.get(url);
            }else if (method==='PUT'){
                return await axios.put(url, data);
            }else if (method==='DELETE'){
                return await axios.delete(url);
            }
        } catch (error) {
            console.error(error);
        }
    }

    async getContacts() {
        const url = "http://localhost:8080/api/contacts";
        const method = 'GET';
        return await this.requestToServer(url, method, {});
    }

    async deleteContact(contact){
        const url="http://localhost:8080/api/users/"+contact.id;
        const method='DELETE';
        return await this.requestToServer(url, method, {});
    }

    async addContact(contact){
        const url="http://localhost:8080/api/addContact";
        const method='POST';
        return await this.requestToServer(url, method, contact);
    }

    async addContacts(contacts){
        const url="http://localhost:8080/api/addContacts";
        const method='POST';
        return await this.requestToServer(url, method, contacts);
    }

    async updateContact(contact){
        const url="http://localhost:8080/api/updateContact/"+contact.id;
        const method='PUT';
        return await this.requestToServer(url, method, contact);
    }
}
