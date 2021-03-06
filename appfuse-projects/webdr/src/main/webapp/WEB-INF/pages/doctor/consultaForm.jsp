<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="consultaDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='consultaDetail.heading'/>"/>
</head>

<s:form id="consultaForm" action="saveConsulta" method="post" validate="true" name="saveConsulta">
    <li class="buttonBar right">
        <c:set var="buttons">
            <s:submit cssClass="button" method="save" key="button.save" theme="simple"/>
            <c:if test="${deleteAccess and not empty consulta.id}">
                <s:submit cssClass="button" method="delete" key="button.delete" onclick="return confirmDelete('esta consulta')" theme="simple"/>
            </c:if>
            <s:submit cssClass="button" method="cancel" key="button.cancel" theme="simple"/>
        </c:set>
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
    <li>
        <s:textfield id="fechaField" key="consulta.fecha" required="true" cssClass="text medium" 
                     readonly="true" onchange="agregarSegundos(this);"/>
        <input type="button" value="..."
               onclick="displayCalendar($('fechaField'), 'dd/mm/yyyy hh:ii', this, true)"/>
    </li>
    <li>
        <div class="left">
            <c:if test="${not empty consulta.id}">
                <s:textfield key="consulta.paciente.fullName" required="true" cssClass="text medium"/>
            </c:if>
            <c:if test="${empty consulta.id}">
                <s:textfield key="pacienteId" required="true" cssClass="text medium" readonly="true" />
            </c:if>
        </div>
        <div>
            <s:textfield label="Doctor Consultado" value="%{fullNameDoctor}" required="true" 
                         disabled="true" cssClass="text medium"/>
        </div>
    </li>
    <li>
        <div class="left">
            <s:textfield key="consulta.medidasPaciente.pesoActual" required="true" 
                         cssClass="text small"/>
        </div>
        <div>
            <s:textfield key="consulta.medidasPaciente.alturaActual" required="true" 
                         cssClass="text small"/>
        </div>
    </li>
    <li>
        <div class="left">
            <s:textfield key="consulta.medidasPaciente.edadActual" required="true" 
                         cssClass="text small"/>
        </div>
        <div>
            <s:select key="consulta.medidasPaciente.edadEnMeses"
                      label="Edad en:"
                      list="#{'true':'Meses', 'false':'A�os'}"
                      required="true"
                      size="1"
                      />
        </div>
    </li>
    <li>
        <div>
            <s:textarea key="consulta.notas.sintomas" required="true" cssClass="text medium" 
                        rows="10" cols="30" wrap="true"/>
        </div>
        <div>
            <s:textarea key="consulta.notas.diagnostico" required="true" cssClass="text medium" 
                        rows="10" cols="30" wrap="true"/>
        </div>
        <div>
            <s:textarea key="consulta.notas.recetario" required="true" cssClass="text medium" 
                        rows="10" cols="30" wrap="true"/>
        </div>
        <div>
            <s:textarea key="consulta.notas.indicaciones" required="true" cssClass="text medium" 
                        rows="10" cols="30" wrap="true"/>
        </div>
    </li>
    
    <s:hidden name="consulta.id" value="%{consulta.id}"/>
    <s:hidden name="consulta.paciente.id" value="%{consulta.paciente.id}"/>
    <s:hidden name="consulta.doctor.id" value="%{consulta.doctor.id}"/>
    
    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</s:form>

<script type="text/javascript">
    //Form.focusFirstElement($("consultaForm"));
    function agregarSegundos(campo) {
        campo.value = campo.value + ":00";
    }
</script>
