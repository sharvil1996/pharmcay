package DrRAJ.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DrRAJ.Bean.ProductBean;
import DrRAJ.Bean.RelatedProductBean;
import DrRAJ.DAO.ProductDAO;
import DrRAJ.DAO.ReletedProductDAO;
import DrRAJ.Utils.GenrateMathodsUtils;
import DrRAJ.Utils.ValidationUtils;

public class ReletedProductInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productId = request.getParameter("txtProductId");
		String relatedProductId = request.getParameter("txtRelatedProductId");

		RelatedProductBean relatedProductBean = new RelatedProductBean();

		boolean isError = false;

		if (ValidationUtils.isEmpty(productId) || productId.equals("0")) {
			isError = true;
			request.setAttribute("product", "<font color=red>* Product is Required</font>");
		} else {
			request.setAttribute("txtProductId", productId);
			relatedProductBean.setProductId(productId);
		}

		if (ValidationUtils.isEmpty(relatedProductId) || relatedProductId.equals("0")) {
			isError = true;
			request.setAttribute("relatedProduct", "<font color=red>* Related Product is Required</font>");
		} else {
			request.setAttribute("txtRelatedProductId", relatedProductId);
			relatedProductBean.setReletedId(relatedProductId);
		}

		if (productId.equals(relatedProductId)) {
			isError = true;
			request.setAttribute("relatedProduct", "<font color=red>* This relation is not required</font>");
		}

		if (isError) {
			request.getRequestDispatcher("RelatedProductInsert.jsp").forward(request, response);
		} else {

			if (new ReletedProductDAO().isExists(relatedProductBean)) {

				request.setAttribute("relatedProduct", "<font color=red>* Already Exists</font>");
				request.getRequestDispatcher("RelatedProductInsert.jsp").forward(request, response);

			} else {
				relatedProductBean.setReletedProductId(GenrateMathodsUtils.getRandomString(15));
				if (new ReletedProductDAO().insert(relatedProductBean)) {
					response.sendRedirect("ReletedProductListServlet");
				} else {
					request.getRequestDispatcher("RelatedProductInsert.jsp").forward(request, response);
				}
			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
