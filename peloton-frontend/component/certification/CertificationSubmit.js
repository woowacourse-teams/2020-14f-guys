import React, { useState } from "react";
import {
  Dimensions,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { Feather } from "@expo/vector-icons";
import * as ImagePicker from "expo-image-picker";

import { raceCertificationState } from "../../state/certification/RaceCertificationState";
import { COLOR } from "../../utils/constants";
import { loadingState } from "../../state/loading/LoadingState";
import {
  getCameraPermission,
  getCameraRollPermission,
} from "../../utils/Permission";

const CertificationSubmit = ({ route }) => {
  const raceCertifications = useRecoilValue(raceCertificationState);
  const setIsLoading = useSetRecoilState(loadingState);

  const { index } = route.params;
  const raceCertification = raceCertifications[index];
  const [photoUri, setPhotoUri] = useState(
    raceCertification.race.certification_example,
  );

  const takePhoto = async () => {
    setIsLoading(true);
    const hasCameraPermission = await getCameraPermission();
    const hasCameraRollPermission = await getCameraRollPermission();
    if (hasCameraPermission === false || hasCameraRollPermission === false) {
      setIsLoading(false);
      return;
    }
    const pickerResult = await ImagePicker.launchCameraAsync({
      allowsEditing: true,
      aspect: [4, 3],
      base64: true,
    });

    if (pickerResult.cancelled === true) {
      setIsLoading(false);
      return;
    }
    const selectedImage = pickerResult.uri;
    setPhotoUri(selectedImage);
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.certificationButton} onPress={takePhoto}>
        <Image source={{ uri: photoUri }} style={styles.certificationExample} />
        <View style={styles.certificationPhoto}>
          <Feather name="camera" size={50} color="white" />
        </View>
      </TouchableOpacity>
      <View style={styles.instructionContainer}>
        <Text style={styles.instruction}>
          {raceCertification.mission.mission_instruction}
        </Text>
      </View>
    </View>
  );
};

const { width } = Dimensions.get("window");

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
  },
  certificationExample: {
    width,
    aspectRatio: 0.8,
    resizeMode: "cover",
  },
  instructionContainer: {
    marginTop: 20,
  },
  instruction: {
    fontSize: 20,
    fontWeight: "300",
    fontStyle: "normal",
    lineHeight: 35,
    color: COLOR.GRAY1,
    textAlign: "center",
  },
  certificationButton: {
    width,
  },
  certificationPhoto: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
    top: 0,
    justifyContent: "center",
    alignItems: "center",
    opacity: 0.7,
  },
});

export default CertificationSubmit;
