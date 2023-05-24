package kr.pet.mvc;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordController {

    private List<MedicalRecord> records = new ArrayList<>();

    public void addMedicalRecord(MedicalRecord record) {
        this.records.add(record);
    }

    public void removeMedicalRecord(String phoneNumber) {
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getPhoneNumber().equals(phoneNumber)) {
                records.remove(i);
                break;
            }
        }
    }

    public List<MedicalRecord> findMedicalRecord(String phoneNumber) {
        List<MedicalRecord> result = new ArrayList<>();

        for (MedicalRecord record : records) {
            if (record.getPhoneNumber().equals(phoneNumber)) {
                result.add(record);
            }
        }

        return result;
    }
}
