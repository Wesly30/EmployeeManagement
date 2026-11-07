import React, { Component } from "react";
import EmployeeService from "../service/EmployeeService";

export default class EmployeeComponent extends Component {
  constructor(props) {
    super(props);

    this.state = { employee: {}, department: {} };
  }

  componentDidMount() {
    EmployeeService.getEmployee().then((response) => {
      this.setState({ employee: response.data.employee });
      this.setState({ department: response.data.department });

      // console.log(this.state.employee);
      // console.log(this.state.department);
    });
  }

  render() {
    return (
      <div>
        <br />
        <br />
        <div className="card col-md-6 offset-md-3">
          <h3 className="text-center card-header"> Employee Details</h3>
          <div className="card-body">
            <div className="row">
              <p>
                <strong>Employee ID: </strong> {this.state.employee.empId}
              </p>
            </div>
            <div className="row">
              <p>
                <strong>Employee Name: </strong> {this.state.employee.empName}
              </p>
            </div>
            <div className="row">
              <p>
                <strong>Employee Mail: </strong> {this.state.employee.empMail}
              </p>
            </div>
            <div className="row">
              <p>
                <strong>Employee Salary: </strong> {this.state.employee.salary}
              </p>
            </div>
          </div>
          <h3 className="text-center card-header"> Department Details</h3>
          <div className="card-body">
            <div className="row">
              <p>
                <strong>Department ID: </strong> {this.state.department.deptId}
              </p>
            </div>
            <div className="row">
              <p>
                <strong>Department Name: </strong>{" "}
                {this.state.department.deptName}
              </p>
            </div>
            <div className="row">
              <p>
                <strong>Department Code: </strong>{" "}
                {this.state.department.deptCode}
              </p>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
