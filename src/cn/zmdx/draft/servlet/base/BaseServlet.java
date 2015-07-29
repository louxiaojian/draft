package cn.zmdx.draft.servlet.base;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseServlet extends HttpServlet {
	private ApplicationContext ctx;
	public void init(ServletConfig config)
		throws ServletException
	{
		super.init(config);
		// ��ȡWebӦ���е�ApplicationContextʵ��
		ctx = WebApplicationContextUtils
			.getWebApplicationContext(getServletContext()); 
	}
	// ����WebӦ���е�Spring����
	public ApplicationContext getCtx()
	{
		return this.ctx;
	}
}
