
import librosa
import tensorflow as tf
import numpy as np
from pydub import AudioSegment

from os.path import dirname,join,splitext

# predict wither the audio is eloquent or stutter
def predict(audio_file, model,input_details,output_details,MINIMUM_LENGTH,data_mapping):
    # extract the (MFCCs) feature
    mfcc = extract_feature(audio_file,MINIMUM_LENGTH)
    if mfcc == "less_than_minimum":
        return "not stutter"

    # mfcc to predict
    mfcc = mfcc[np.newaxis, ..., np.newaxis]
    mfcc = mfcc.reshape(1, 13, 44, 1)

    model.set_tensor(input_details[0]['index'], mfcc)
    model.invoke()
    tflite_results = model.get_tensor(output_details[0]['index'])
    predicted_index = np.argmax(tflite_results)

    # If the index is not 0 (not stutter), make the index 1 (stutter)
    if predicted_index == 7:
        predicted_index = 0
    else:
        predicted_index = 1

    # return the map of the index: 0 (not stutter), 1 (stutter)
    return data_mapping[predicted_index]


# extract the (MFCCs) feature
def extract_feature(audio_file,MINIMUM_LENGTH):
    # load audio file
    myaudio, sample_rate = librosa.load(audio_file)


    # check if the audio files is equal or more that the minimum length
    if len(myaudio) >= MINIMUM_LENGTH:
        # Make a constant sound length
        myaudio = myaudio[:MINIMUM_LENGTH]

        # extract the feature (MFCCs)
        mfcc = librosa.feature.mfcc(myaudio, sample_rate, n_mfcc=13, n_fft=2048,
                                    hop_length=512)
        delta = librosa.feature.delta(mfcc, order=2, mode='constant')

        return delta.T
    return "less_than_minimum"


# Calculate stuttering_severity
def stuttering_severity(number_of_segment, stuttering_rate):
    # (number of segment classified as stutter divided by
    # number of total segment)
    # multiply by 100 <-- for rate
    stuttering_rate = stuttering_rate / number_of_segment * 100

    # To print severity as word (Not stutter, Low, Moderate, High)
    if stuttering_rate > 66:
        severity = "High"
    elif 66 >= stuttering_rate > 33:
        severity = "Moderate"
    elif stuttering_rate > 0:
        severity = "Low"
    else:
        severity = "Not stutter: Your speech is eloquent"

    return severity



def segmentation(file):

    myaudio = AudioSegment.from_file(file, "wav")
    audio_chunks = myaudio[::5000]

    return audio_chunks

def main (path):
    # tflite_model_file = "last_model_tf.tflite"
    tflite_model_file2 = join(dirname(__file__),"last_model_tf.tflite")
    #file = join(dirname(__file__),"Seg7_Audio5.wav")

    MINIMUM_LENGTH = 22050

    data_mapping = [
        "not stutter",
        "stutter"
    ]


    interpreter = tf.lite.Interpreter(model_path=tflite_model_file2)
    interpreter.allocate_tensors()

    MODEL = interpreter


    input_details = interpreter.get_input_details()
    output_details = interpreter.get_output_details()
    MODEL = interpreter

    #prediction = predict(file, MODEL,input_details,output_details,MINIMUM_LENGTH,data_mapping)

    segment = 0
    stutter = 0
    #for i in (path+"*.wav"):
    audio_chunks = segmentation(path)
    root = splitext(path)[-2]


    for i, chunk in enumerate(audio_chunks):
        segment = i+1
        chunkfile = path+"chunk{0}.wav".format(i)
        chunk.export(chunkfile, format="wav")
        prediction = predict(chunkfile, MODEL,input_details,output_details,MINIMUM_LENGTH,data_mapping)
        if prediction == "stutter":
            stutter += 1


    severity = stuttering_severity(segment, stutter)


    return (severity)

