import axios from "axios";

const EMPLOYEE_API_BASE_URL = "http://localhost:4040/employees";

const EMPLOYEE_ID = 6;

class EmployeeService {
  getEmployee() {
    return axios.get(
      EMPLOYEE_API_BASE_URL + "/" + "getEmployee" + "/" + EMPLOYEE_ID
    );
  }
}

export default new EmployeeService();
