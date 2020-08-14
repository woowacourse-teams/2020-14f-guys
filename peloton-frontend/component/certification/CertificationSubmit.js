import React from "react";
import {
  Dimensions,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { useRecoilValue } from "recoil";
import { Feather } from "@expo/vector-icons";

import { raceCertificationState } from "../../state/certification/RaceCertificationState";
import { COLOR } from "../../utils/constants";

const CertificationSubmit = ({ route }) => {
  const raceCertifications = useRecoilValue(raceCertificationState);

  const { index } = route.params;
  const raceCertification = raceCertifications[index];

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.certificationButton}>
        <Image
          source={{ uri: raceCertification.race.certification_example }}
          style={styles.certificationExample}
        />
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
