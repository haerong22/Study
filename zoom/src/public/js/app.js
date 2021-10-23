const socket = io();

const myFace = document.getElementById("myFace");
const muteBtn = document.getElementById("mute");
const cameraBtn = document.getElementById("camera");
const camerasSelect = document.getElementById("cameras");

let myStream;
let mute = false;
let camera = false;



async function getCameras() {
    try {
        const devices = await navigator.mediaDevices.enumerateDevices();
        const cameras = devices.filter(device => device.kind === 'videoinput');
        const currentCamera = myStream.getVideoTracks()[0];

        cameras.forEach(camera => {
            const option = document.createElement("option");
            option.value = camera.deviceId;
            option.innerText = camera.label;
            if (currentCamera.label === camera.label) {
                option.selected = true;
            }
            camerasSelect.appendChild(option);
        });
    } catch (e) {
        console.log(e);
    }
}

async function getMedia(deviceId) {
    const initialConstraints = {
        audio: true,
        video: { facingMode: "user"},
    }
    const cameraConstraints = {
        audio: true,
        video: { deviceId: { exact : deviceId }},
    }

    try {
        myStream = await navigator.mediaDevices.getUserMedia(
            deviceId ? cameraConstraints : initialConstraints
        );

        myFace.srcObject = myStream;

        if (!deviceId) {
            await getCameras();    
        }
    } catch (e) {
        console.log(e);
    }
}

getMedia();

function handleMuteClick() {
    myStream
        .getAudioTracks()
        .forEach((track) => (track.enabled = !track.enabled));
        
    if (mute) {
        muteBtn.innerText = "Mute";
        mute = false;
    } else {
        muteBtn.innerText = "UnMute";
        mute = true;
    }
}

function handleCameraClick() {
    myStream
        .getVideoTracks()
        .forEach((track) => (track.enabled = !track.enabled));

    if (camera) {
        cameraBtn.innerText = "Turn Camera Off";
        camera = false;
    } else {
        cameraBtn.innerText = "Turn Camera On";
        camera = true;
    }
}

async function handleCameraChange() {
    await getMedia(camerasSelect.value);
}
muteBtn.addEventListener("click", handleMuteClick);
cameraBtn.addEventListener("click", handleCameraClick);
camerasSelect.addEventListener("input", handleCameraChange);