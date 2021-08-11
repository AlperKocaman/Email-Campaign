import axios from 'axios';

export default class ClickService {

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

    async sendClick(id){
        const url="http://localhost:8080/api/click/" + id;
        const method='POST';
        return await this.requestToServer(url, method, {});
    }

}
