import React from "react";
import { StyleSheet } from "react-native";
import Swiper from "react-native-swiper";
import RaceCertificationImage from "./RaceCertificationImage";
import { useRecoilValue } from "recoil/dist";
import { certificationsState } from "../../../state/certification/CertificationState";

const RaceCertificationImages = () => {
  const certifications = useRecoilValue(certificationsState);

  return (
    <Swiper
      style={styles.container}
      loop={true}
      showsPagination={true}
      key={certifications ? certifications.length : 1}
    >
      {certifications && certifications.length > 0 ? (
        certifications.map((item, index) => (
          <RaceCertificationImage
            key={index}
            certification={item}
            touchable={true}
            name="CertificationDetail"
            params={{ id: item.id }}
          />
        ))
      ) : (
        <RaceCertificationImage key={0} item={null} touchable={false} />
      )}
    </Swiper>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 600,
  },
});

export default RaceCertificationImages;
