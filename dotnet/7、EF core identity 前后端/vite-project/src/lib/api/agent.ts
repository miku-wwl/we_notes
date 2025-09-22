import axios from "axios";
import { store } from "../stores/store";
// import { toast } from "react-toastify";
// import { router } from "../../app/router/Routes";

const sleep = (delay: number) => {
    return new Promise(resolve => {
        setTimeout(resolve, delay)
    });
}

const agent = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    withCredentials: true
});

agent.interceptors.request.use(config => {
    store.uiStore.isBusy();

    const token = "CfDJ8E-1y1cDxVBCpocCozAnHHWxBx4Y4fboKjcJsCH6Qrc84p0IPapZB_R7mYHHaDGKY_S21WYjcv4W8DN4yw_f5UPP-Pjf5oECncuds-3_QY_VoAUHUo9Kkw9S-BYu7ytM03zOiAHZIFQ1yDc5iHofluTkXe0Ur8lCWigIg-SN3N3AYiidE_jo8RLC-TRe-3jFo7tcZ9Ojpv1x2boddQ_SNe2y2k8awaHNVxwjFsd4DfBPhopiDpd0nYJ6B63yxujT8zHlf-q8-Okdd2y_gN9EWvnMq7cL0F4-YxgmdB_MAwL8YRfcS1o8_J6Cn7V8flv2dyY79YGUPb3sR3J2DGs-u2LzfnPEe-my5Y91UZmMFsZYzsMqwqok8Rt6ZBACtJSX9fX8KlV0L7XBkfnIIa-DO7hR5v2fpjh4kP8o5LERyPxxbs10H4K1xW5yWg68S3mNqCB4kM22FcgrQTY7oWYX8ow4rxSpronv1E5JzmPD5S0atEuctLPLT3MQKjh4XXWGUPW9LdyLt-U0dicAf8LxuQ-yDCPX2IK3hI632ULkEktbxU2s_eJ7MzRB-_ZLWaj3cLUddSnI9w824WU3CieLVTPSXH-3Qgi-FSVVEyO5tIsE5Ov1A7hzJWgvwK-lPBXlElgOIab1wBKb2Hap_M7_UYEtTy2pkXBJxtq_vtLdmqz_";
    config.headers.Authorization = 'Bearer ' + token;

    return config;
})

agent.interceptors.response.use(
    async response => {
        await sleep(100);
        store.uiStore.isIdle()
        return response;
    },
    async error => {
        await sleep(1000);
        // store.uiStore.isIdle();

        // const { status, data } = error.response;
        // switch (status) {
        //     case 400:
        //         if (data.errors) {
        //             const modalStateErrors = [];
        //             for (const key in data.errors) {
        //                 if (data.errors[key]) {
        //                     modalStateErrors.push(data.errors[key]);
        //                 }
        //             }
        //             throw modalStateErrors.flat();
        //         } else {
        //             toast.error(data);
        //         }
        //         break;
        //     case 401:
        //         toast.error('Unauthorised');
        //         break;
        //     case 404:
        //         router.navigate('/not-found');
        //         break;
        //     case 500:
        //         router.navigate('/server-error', {state: {error: data}})
        //         break;
        //     default:
        //         break;
        // }

        return Promise.reject(error);
    }
);

export default agent;